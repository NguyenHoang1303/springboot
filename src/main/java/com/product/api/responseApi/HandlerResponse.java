package com.product.api.responseApi;

import lombok.*;

import java.util.HashMap;

@Getter
@Setter
public class HandlerResponse {

    public final static String TYPE = "type";
    public final static String ITEMS = "items";
    public final static String ERRORS = "errors";

    public final static String PRODUCTS = "products";
    public final static String CATEGORY = "category";
    public static final Object ORDER = "orders";
    public static final Object ORDER_DETAIL = "order detail";


    private int status;
    private String message;
    private HashMap<String, Object> body;



    public HandlerResponse() {
    }

    public HandlerResponse(int status, String message, HashMap<String, Object> data) {
        this.status = status;
        this.message = message;
        this.body = data;
    }

    public static final class HandlerResponseBuilder {
        private int status;
        private String message;
        private HashMap<String, Object> data;


        private HandlerResponseBuilder() {
        }

        public static HandlerResponseBuilder aHandlerResponse() {
            return new HandlerResponseBuilder();
        }

        public HandlerResponseBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public HandlerResponseBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public HandlerResponseBuilder withData(HashMap<String, Object> data) {
            this.data = data;
            return this;
        }

        public HandlerResponseBuilder addData(String key, Object value) {
            if(this.data == null){
                this.data = new HashMap<>();
            }
            this.data.put(key, value);
            return this;
        }

        public HandlerResponse build() {
            HandlerResponse handlerResponse = new HandlerResponse();
            handlerResponse.body = this.data;
            handlerResponse.message = this.message;
            handlerResponse.status = this.status;
            return handlerResponse;
        }


    }
}
