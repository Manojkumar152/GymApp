package app.com.gymapp.Models;

import java.util.List;

public class ProfileData {

    /**
     * status : 1
     * error_code : 200
     * error : Success / All ok
     * result : [{"first_name":"gymowner","last_name":"singh","birth_date":"2019-07-08","address":"fsdfs","city":"dsfds","state":"fdsf","mobile":"9746589560","phone":"","email":"little.kumar@lbmsolutions.insad","username":"owner","password":"$2y$10$JeY3.TQmC.VJJK9upTz40.q5diwcP4G7/ly7pQ3f0oTUDdr4Kkk3C","image":"http://gymdemo.lbmsolutions.in/webroot/upload/Thumbnail-img.png","imageName":"Thumbnail-img.png"}]
     */

    private String status;
    private int error_code;
    private String error;
    private List<ResultBean> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * first_name : gymowner
         * last_name : singh
         * birth_date : 2019-07-08
         * address : fsdfs
         * city : dsfds
         * state : fdsf
         * mobile : 9746589560
         * phone :
         * email : little.kumar@lbmsolutions.insad
         * username : owner
         * password : $2y$10$JeY3.TQmC.VJJK9upTz40.q5diwcP4G7/ly7pQ3f0oTUDdr4Kkk3C
         * image : http://gymdemo.lbmsolutions.in/webroot/upload/Thumbnail-img.png
         * imageName : Thumbnail-img.png
         */

        private String first_name;
        private String last_name;
        private String birth_date;
        private String address;
        private String city;
        private String state;
        private String mobile;
        private String phone;
        private String email;
        private String username;
        private String password;
        private String image;
        private String imageName;

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getBirth_date() {
            return birth_date;
        }

        public void setBirth_date(String birth_date) {
            this.birth_date = birth_date;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }
    }
}
