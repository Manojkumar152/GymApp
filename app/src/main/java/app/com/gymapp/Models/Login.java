package app.com.gymapp.Models;

import java.util.List;

public class Login {

    /**
     * status : 1
     * error_code : 200
     * error : Success / All ok
     * result : [{"id":"62","gym_id":"22","user_added_by":"61","activated":"1","role_name":"administrator","member_id":null,"token":"","first_name":"gymowner","middle_name":"jaswal","last_name":"singh","member_type":null,"role":null,"plan_id":null,"s_specialization":null,"gender":"male","birth_date":"2019-07-08","assign_class":null,"assign_group":null,"address":"fsdfs","city":"dsfds","state":"fdsf","zipcode":"223444","mobile":"9746589560","phone":"","email":"little.kumar@lbmsolutions.insad","weight":null,"height":null,"chest":null,"waist":null,"thing":null,"arms":null,"fat":null,"username":"owner","password":"$2y$10$phWWcr5U9BmVjABWpbtoeOg08zS7sOQTMSI1n/C3vmVWm9PTDw2QO","image":"Thumbnail-img.png","assign_staff_mem":null,"intrested_area":null,"status":"0","g_source":null,"referrer_by":null,"inquiry_date":null,"trial_end_date":null,"selected_membership":null,"membership_status":null,"membership_valid_from":"2019-09-23","membership_valid_to":"2020-03-31","first_pay_date":null,"payment":null,"created_by":null,"created_date":"2019-09-23","alert_sent":null,"Image":"http://gymdemo.lbmsolutions.in/webroot/upload/Thumbnail-img.png"}]
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
         * id : 62
         * gym_id : 22
         * user_added_by : 61
         * activated : 1
         * role_name : administrator
         * member_id : null
         * token :
         * first_name : gymowner
         * middle_name : jaswal
         * last_name : singh
         * member_type : null
         * role : null
         * plan_id : null
         * s_specialization : null
         * gender : male
         * birth_date : 2019-07-08
         * assign_class : null
         * assign_group : null
         * address : fsdfs
         * city : dsfds
         * state : fdsf
         * zipcode : 223444
         * mobile : 9746589560
         * phone :
         * email : little.kumar@lbmsolutions.insad
         * weight : null
         * height : null
         * chest : null
         * waist : null
         * thing : null
         * arms : null
         * fat : null
         * username : owner
         * password : $2y$10$phWWcr5U9BmVjABWpbtoeOg08zS7sOQTMSI1n/C3vmVWm9PTDw2QO
         * image : Thumbnail-img.png
         * assign_staff_mem : null
         * intrested_area : null
         * status : 0
         * g_source : null
         * referrer_by : null
         * inquiry_date : null
         * trial_end_date : null
         * selected_membership : null
         * membership_status : null
         * membership_valid_from : 2019-09-23
         * membership_valid_to : 2020-03-31
         * first_pay_date : null
         * payment : null
         * created_by : null
         * created_date : 2019-09-23
         * alert_sent : null
         * Image : http://gymdemo.lbmsolutions.in/webroot/upload/Thumbnail-img.png
         */

        private String id;
        private String gym_id;
        private String user_added_by;
        private String activated;
        private String role_name;
        private Object member_id;
        private String token;
        private String first_name;
        private String middle_name;
        private String last_name;
        private Object member_type;
        private Object role;
        private Object plan_id;
        private Object s_specialization;
        private String gender;
        private String birth_date;
        private Object assign_class;
        private Object assign_group;
        private String address;
        private String city;
        private String state;
        private String zipcode;
        private String mobile;
        private String phone;
        private String email;
        private Object weight;
        private Object height;
        private Object chest;
        private Object waist;
        private Object thing;
        private Object arms;
        private Object fat;
        private String username;
        private String password;
        private String image;
        private Object assign_staff_mem;
        private Object intrested_area;
        private String status;
        private Object g_source;
        private Object referrer_by;
        private Object inquiry_date;
        private Object trial_end_date;
        private Object selected_membership;
        private Object membership_status;
        private String membership_valid_from;
        private String membership_valid_to;
        private Object first_pay_date;
        private Object payment;
        private Object created_by;
        private String created_date;
        private Object alert_sent;
        private String Image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGym_id() {
            return gym_id;
        }

        public void setGym_id(String gym_id) {
            this.gym_id = gym_id;
        }

        public String getUser_added_by() {
            return user_added_by;
        }

        public void setUser_added_by(String user_added_by) {
            this.user_added_by = user_added_by;
        }

        public String getActivated() {
            return activated;
        }

        public void setActivated(String activated) {
            this.activated = activated;
        }

        public String getRole_name() {
            return role_name;
        }

        public void setRole_name(String role_name) {
            this.role_name = role_name;
        }

        public Object getMember_id() {
            return member_id;
        }

        public void setMember_id(Object member_id) {
            this.member_id = member_id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getMiddle_name() {
            return middle_name;
        }

        public void setMiddle_name(String middle_name) {
            this.middle_name = middle_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public Object getMember_type() {
            return member_type;
        }

        public void setMember_type(Object member_type) {
            this.member_type = member_type;
        }

        public Object getRole() {
            return role;
        }

        public void setRole(Object role) {
            this.role = role;
        }

        public Object getPlan_id() {
            return plan_id;
        }

        public void setPlan_id(Object plan_id) {
            this.plan_id = plan_id;
        }

        public Object getS_specialization() {
            return s_specialization;
        }

        public void setS_specialization(Object s_specialization) {
            this.s_specialization = s_specialization;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getBirth_date() {
            return birth_date;
        }

        public void setBirth_date(String birth_date) {
            this.birth_date = birth_date;
        }

        public Object getAssign_class() {
            return assign_class;
        }

        public void setAssign_class(Object assign_class) {
            this.assign_class = assign_class;
        }

        public Object getAssign_group() {
            return assign_group;
        }

        public void setAssign_group(Object assign_group) {
            this.assign_group = assign_group;
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

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
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

        public Object getWeight() {
            return weight;
        }

        public void setWeight(Object weight) {
            this.weight = weight;
        }

        public Object getHeight() {
            return height;
        }

        public void setHeight(Object height) {
            this.height = height;
        }

        public Object getChest() {
            return chest;
        }

        public void setChest(Object chest) {
            this.chest = chest;
        }

        public Object getWaist() {
            return waist;
        }

        public void setWaist(Object waist) {
            this.waist = waist;
        }

        public Object getThing() {
            return thing;
        }

        public void setThing(Object thing) {
            this.thing = thing;
        }

        public Object getArms() {
            return arms;
        }

        public void setArms(Object arms) {
            this.arms = arms;
        }

        public Object getFat() {
            return fat;
        }

        public void setFat(Object fat) {
            this.fat = fat;
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

        public Object getAssign_staff_mem() {
            return assign_staff_mem;
        }

        public void setAssign_staff_mem(Object assign_staff_mem) {
            this.assign_staff_mem = assign_staff_mem;
        }

        public Object getIntrested_area() {
            return intrested_area;
        }

        public void setIntrested_area(Object intrested_area) {
            this.intrested_area = intrested_area;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getG_source() {
            return g_source;
        }

        public void setG_source(Object g_source) {
            this.g_source = g_source;
        }

        public Object getReferrer_by() {
            return referrer_by;
        }

        public void setReferrer_by(Object referrer_by) {
            this.referrer_by = referrer_by;
        }

        public Object getInquiry_date() {
            return inquiry_date;
        }

        public void setInquiry_date(Object inquiry_date) {
            this.inquiry_date = inquiry_date;
        }

        public Object getTrial_end_date() {
            return trial_end_date;
        }

        public void setTrial_end_date(Object trial_end_date) {
            this.trial_end_date = trial_end_date;
        }

        public Object getSelected_membership() {
            return selected_membership;
        }

        public void setSelected_membership(Object selected_membership) {
            this.selected_membership = selected_membership;
        }

        public Object getMembership_status() {
            return membership_status;
        }

        public void setMembership_status(Object membership_status) {
            this.membership_status = membership_status;
        }

        public String getMembership_valid_from() {
            return membership_valid_from;
        }

        public void setMembership_valid_from(String membership_valid_from) {
            this.membership_valid_from = membership_valid_from;
        }

        public String getMembership_valid_to() {
            return membership_valid_to;
        }

        public void setMembership_valid_to(String membership_valid_to) {
            this.membership_valid_to = membership_valid_to;
        }

        public Object getFirst_pay_date() {
            return first_pay_date;
        }

        public void setFirst_pay_date(Object first_pay_date) {
            this.first_pay_date = first_pay_date;
        }

        public Object getPayment() {
            return payment;
        }

        public void setPayment(Object payment) {
            this.payment = payment;
        }

        public Object getCreated_by() {
            return created_by;
        }

        public void setCreated_by(Object created_by) {
            this.created_by = created_by;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }

        public Object getAlert_sent() {
            return alert_sent;
        }

        public void setAlert_sent(Object alert_sent) {
            this.alert_sent = alert_sent;
        }
    }
}
