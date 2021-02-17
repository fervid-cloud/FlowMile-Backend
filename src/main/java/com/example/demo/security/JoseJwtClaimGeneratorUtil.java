package com.example.demo.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

//@Component
public class JoseJwtClaimGeneratorUtil {

    @Value("${jwt.company-secret-key}")
    private String companySecretKey;

    //retrieve employeeName from jwt token
    public String getUsernameFromToken(String token) throws ParseException, JOSEException {
        return getTokenKeyValueForClaims(token).get("sub").toString();
    }

    private Map<String, Object> getTokenKeyValueForClaims(String token) throws ParseException, JOSEException {

        Map<String, Object> claims;

        try {

            JWSVerifier verifier = new MACVerifier(companySecretKey);

            SignedJWT signedJWT = SignedJWT.parse(token);
            signedJWT.verify(verifier);

            claims = signedJWT.getJWTClaimsSet().getClaims();

        } catch (ParseException e) {
            throw new ParseException("Payload of JWS object is not a valid JSON object", e.getErrorOffset());
        } catch (JOSEException e) {
            throw new JOSEException("JOSE exception for token: ", e.getCause());
        }

        return claims;
    }

    public String generateJwtToken(UserDetails userDetails) throws Exception {

        try {

            JWSSigner signer = new MACSigner(companySecretKey);

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                                         .subject(userDetails.getUsername())
                                         .issuer("your issuer")// On our case it is IBM thingy
                                         .expirationTime(new Date(System.currentTimeMillis() + 60 * 1000)) //Expire in a minute
                                         .audience("demo")
                                         .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

            signedJWT.sign(signer);

            return signedJWT.serialize();

        } catch (Exception e) {
            throw new Exception("JWT Signing has failed: ", e.getCause());
        }
    }

    public Boolean validateTokenTimestamp(String token, UserDetails userDetails) throws ParseException, JOSEException {

        SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
        final Date expiration = formatter.parse(getTokenKeyValueForClaims(token).get("exp").toString());
        boolean hasExpired = expiration.before(new Date());

        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !hasExpired);
    }
}