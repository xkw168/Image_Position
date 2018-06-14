package com.example.xkw.imagepos;

import java.util.List;

/**
 * Created by xkw on 2018/6/14.
 */

public class MapBean {

    /**
     * results : []
     ** status : OK
     */

    private String status;
    private List<ResultsBean> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * address_components : [{"long_name":"Unnamed Road","short_name":"Unnamed Road","types":["route"]},{"long_name":"临泽县","short_name":"临泽县","types":["political","sublocality","sublocality_level_1"]},{"long_name":"张掖市","short_name":"张掖市","types":["locality","political"]},{"long_name":"甘肃省","short_name":"甘肃省","types":["administrative_area_level_1","political"]},{"long_name":"中国","short_name":"CN","types":["country","political"]}]
         * formatted_address : Unnamed Road, 临泽县张掖市甘肃省中国
         * geometry : {"bounds":{"northeast":{"lat":38.97788329999999,"lng":100.0311111},"southwest":{"lat":38.97584579999999,"lng":100.0189925}},"location":{"lat":38.9759475,"lng":100.0245248},"location_type":"GEOMETRIC_CENTER","viewport":{"northeast":{"lat":38.9782135302915,"lng":100.0311111},"southwest":{"lat":38.9755155697085,"lng":100.0189925}}}
         * place_id : ChIJGWAg_3kEtTcRogIgUgTvkWQ
         * types : ["route"]
         */

        private String formatted_address;
        private List<AddressComponentsBean> address_components;
        private List<String> types;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public List<AddressComponentsBean> getAddress_components() {
            return address_components;
        }

        public void setAddress_components(List<AddressComponentsBean> address_components) {
            this.address_components = address_components;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public static class AddressComponentsBean {
            /**
             * long_name : Unnamed Road
             * short_name : Unnamed Road
             * types : ["route"]
             */

            private String long_name;
            private String short_name;
            private List<String> types;

            public String getLong_name() {
                return long_name;
            }

            public void setLong_name(String long_name) {
                this.long_name = long_name;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public List<String> getTypes() {
                return types;
            }

            public void setTypes(List<String> types) {
                this.types = types;
            }
        }
    }
}
