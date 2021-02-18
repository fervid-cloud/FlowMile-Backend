package com.example.demo.dto;



public class ResponseModel {
    /**
     * The successStatus.
     * successStatus of the operation. 0 - Failed | 1 - Successfuld
     */

    private int successStatus;

    /**
     * The Message.
     */
    private String message;

    /**
     * The Object.
     */
    private Object object;

    /**
     * Instantiates a new Response model.
     *
     * @param successStatus  the successStatus
     * @param message the message
     * @param object  the object
     */
    public ResponseModel(int successStatus, String message, Object object) {
        this.successStatus = successStatus;
        this.message = message;
        this.object = object;
    }

    /**
     * Instantiates a new Response model.
     */
    public ResponseModel() {
    }

    /**
     * Builder response model builder.
     *
     * @return the response model builder
     */
    public static ResponseModelBuilder builder() {
        return new ResponseModelBuilder();
    }

    /**
     * Gets successStatus.
     *
     * @return the successStatus
     */
    public int getsuccessStatus() {
        return this.successStatus;
    }


    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }


    /**
     * Gets object.
     *
     * @return the object
     */
    public Object getObject() {
        return this.object;
    }

    /**
     * The type Response model builder.
     */
    public static class ResponseModelBuilder {
        /**
         * The successStatus.
         */
        private int successStatus;
        /**
         * The Message.
         */
        private String message;
        /**
         * The Object.
         */
        private Object object;
        /**
         * The Id.
         */
        private String id;

        /**
         * Instantiates a new Response model builder.
         */
        ResponseModelBuilder() {
        }

        /**
         * successStatus response model . response model builder.
         *
         * @param successStatus the successStatus
         * @return the response model . response model builder
         */
        public ResponseModel.ResponseModelBuilder successStatus(int successStatus) {
            this.successStatus = successStatus;
            return this;
        }

        /**
         * Message response model . response model builder.
         *
         * @param message the message
         * @return the response model . response model builder
         */
        public ResponseModel.ResponseModelBuilder message(String message) {
            this.message = message;
            return this;
        }

        /**
         * Object response model . response model builder.
         *
         * @param object the object
         * @return the response model . response model builder
         */
        public ResponseModel.ResponseModelBuilder object(Object object) {
            this.object = object;
            return this;
        }

        /**
         * Id response model . response model builder.
         *
         * @param id the id
         * @return the response model . response model builder
         */
        public ResponseModel.ResponseModelBuilder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Build response model.
         *
         * @return the response model
         */
        public ResponseModel build() {
            return new ResponseModel(successStatus, message, object);
        }

    }


    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "ResponseModel{" +
                   "successStatus=" + successStatus +
                   ", message='" + message + '\'' +
                   ", object=" + object +
                   '}';
    }

}

