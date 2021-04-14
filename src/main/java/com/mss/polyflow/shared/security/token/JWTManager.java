package com.mss.polyflow.shared.security.token;

import com.mss.polyflow.shared.exception.TokenExpiredException;
import com.mss.polyflow.shared.model.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import javax.security.auth.login.CredentialException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTManager {

    /***
     * NEED TO VERIFY BELOW DETAIL
     * The secret length must be at least 256 bits i.e 16(for java) characters otherwise com.nimbusds.jose.KeyLengthException:
     * It depends what is the character and what encoding it is in:
     * An ASCII character in 8-bit ASCII encoding is 8 bits (1 byte), though it can fit in 7 bits.
     * An ASCII character in UTF-8 is 8 bits (1 byte), and in UTF-16 - 16 bits.
     * An ISO-8895-1 character in ISO-8859-1 encoding is 8 bits (1 byte).
     * A Unicode character in UTF-8 encoding is between 8 bits (1 byte) and 32 bits (4 bytes).
     * side-info - There are 8 bits in a byte,
     * so in java char type data size is 2 bytes
     * char     2 bytes
     * long	    8 bytes
     * float	4 bytes
     * double	8 bytes
     * boolean	1 bit
     */
    @Value("${mac.jwtSecret}")
    private String jwtSecret;

    public String getUsername(String givenToken) {
        JWTClaimsSet claimsSet = verifyToken(givenToken);
        return (claimsSet != null) ? claimsSet.getSubject() : null;
    }

    public String generateToken(User user)  {
        try {

            ZonedDateTime currentTime = java.time.ZonedDateTime.now();

            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .issuer("mss")
                .subject(user.getUsername())
                .issueTime(Date.from(currentTime.toInstant()))
                .expirationTime(Date.from(currentTime.plusDays(2).toInstant()))
                .claim("useFor", "testing")
                .notBeforeTime(Date.from(currentTime.toInstant()))
//                .jwtID(UUID.randomUUID().toString())  //useful for stateful management
                .build();

            JWSSigner macSigner = new MACSigner(jwtSecret);
            JWSAlgorithm algorithm = JWSAlgorithm.HS256;
            JWSHeader jwsHeader = new JWSHeader.Builder(algorithm)
                .contentType("JSON")
                .type(new JOSEObjectType("CUSTOM-JWT"))
                .customParam("customParamForHeader1", "test")
                .build();

            SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaimsSet);
            signedJWT.sign(macSigner);
            String accessToken = signedJWT.serialize();
            return accessToken;

        } catch (JOSEException joseException) {
            joseException.printStackTrace();
            return null;
        }
    }

    private JWTClaimsSet verifyToken(final String token) {

        try {

            SignedJWT signedParsedJWT = SignedJWT.parse(token);

            JWSVerifier jwsVerifier = new MACVerifier(jwtSecret);
            Boolean verificationStatus = signedParsedJWT.verify(jwsVerifier);
            if(!verificationStatus) {
                throw new CredentialException();
            }


            if(isTokenExpired(signedParsedJWT)) {
                throw new TokenExpiredException();
            }

            System.out.println("Token verification status is : " + verificationStatus);
            return signedParsedJWT.getJWTClaimsSet();

        } catch (ParseException e) {
            System.out.println("Payload of JWS object is not a valid JSON object");
            e.printStackTrace();
        } catch (JOSEException e) {
            System.out.println("JOSE exception for token: ");
            e.printStackTrace();
        } catch (CredentialException e) {
            System.out.println("JWT is parsed correctly, but Incorrect signature so printing Invalid Credential");
            e.printStackTrace();
        }
        return null;
    }

    private boolean isTokenExpired(SignedJWT signedParsedJWT) throws ParseException {
        Instant curTime = Instant.now();
        Instant expiryTime = signedParsedJWT.getJWTClaimsSet().getExpirationTime().toInstant();
        return curTime.isAfter(expiryTime);
    }

}
