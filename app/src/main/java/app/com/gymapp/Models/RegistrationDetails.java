package app.com.gymapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegistrationDetails {


    /**
     * status : 1
     * error_code : 200
     * error : Success / All ok
     * username : ["ownernew","superadminstaff","owner","Pk","Sk","staff","member","accountant","abc123","def123","Sunny","Libra","Jk","Dalip","Raj","Saroj","Satnam","alpha","arvind","aarav","sl"]
     * result : {"member_id":"M830219","class":[{"id":"1","class_name":"Aerobic"},{"id":"2","class_name":"abc"},{"id":"3","class_name":"Yoga"},{"id":"4","class_name":"Gym"},{"id":"5","class_name":"Arbice"},{"id":"6","class_name":"Dance"},{"id":"7","class_name":"Yoga"},{"id":"8","class_name":"cyclejj"},{"id":"9","class_name":"Yoga"},{"id":"10","class_name":"Dance"},{"id":"11","class_name":"Cjjs"},{"id":"12","class_name":"aerobics"}],"group":[{"id":"1","name":"Aaa"},{"id":"2","name":"Gym "},{"id":"3","name":"Abcd"},{"id":"4","name":"Weight loss"},{"id":"5","name":"grouptest"},{"id":"6","name":"Weight loss"},{"id":"7","name":"losse"}],"interest_area":[{"id":"1","gym_id":"3","interest":"Gym"},{"id":"2","gym_id":"3","interest":"Gym"},{"id":"3","gym_id":"12","interest":"Gym"},{"id":"4","gym_id":"27","interest":"Gym"}],"membership":[{"id":"1","membership_label":"membership demo"},{"id":"2","membership_label":"Gold"},{"id":"3","membership_label":"Diamond"},{"id":"4","membership_label":"Silver"},{"id":"5","membership_label":"Silver"},{"id":"6","membership_label":"Gold"},{"id":"7","membership_label":"Diamond"},{"id":"8","membership_label":"Month"},{"id":"9","membership_label":"momthsss"},{"id":"10","membership_label":"membership"},{"id":"11","membership_label":"Silver"},{"id":"12","membership_label":"Gold"},{"id":"13","membership_label":"platinum"},{"id":"14","membership_label":"platinum"}]}
     */

    private String status;
    private int error_code;
    private String error;
    private ResultBean result;
    private List<String> username;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public List<String> getUsername() {
        return username;
    }

    public void setUsername(List<String> username) {
        this.username = username;
    }

    public static class ResultBean {
        /**
         * member_id : M830219
         * class : [{"id":"1","class_name":"Aerobic"},{"id":"2","class_name":"abc"},{"id":"3","class_name":"Yoga"},{"id":"4","class_name":"Gym"},{"id":"5","class_name":"Arbice"},{"id":"6","class_name":"Dance"},{"id":"7","class_name":"Yoga"},{"id":"8","class_name":"cyclejj"},{"id":"9","class_name":"Yoga"},{"id":"10","class_name":"Dance"},{"id":"11","class_name":"Cjjs"},{"id":"12","class_name":"aerobics"}]
         * group : [{"id":"1","name":"Aaa"},{"id":"2","name":"Gym "},{"id":"3","name":"Abcd"},{"id":"4","name":"Weight loss"},{"id":"5","name":"grouptest"},{"id":"6","name":"Weight loss"},{"id":"7","name":"losse"}]
         * interest_area : [{"id":"1","gym_id":"3","interest":"Gym"},{"id":"2","gym_id":"3","interest":"Gym"},{"id":"3","gym_id":"12","interest":"Gym"},{"id":"4","gym_id":"27","interest":"Gym"}]
         * membership : [{"id":"1","membership_label":"membership demo"},{"id":"2","membership_label":"Gold"},{"id":"3","membership_label":"Diamond"},{"id":"4","membership_label":"Silver"},{"id":"5","membership_label":"Silver"},{"id":"6","membership_label":"Gold"},{"id":"7","membership_label":"Diamond"},{"id":"8","membership_label":"Month"},{"id":"9","membership_label":"momthsss"},{"id":"10","membership_label":"membership"},{"id":"11","membership_label":"Silver"},{"id":"12","membership_label":"Gold"},{"id":"13","membership_label":"platinum"},{"id":"14","membership_label":"platinum"}]
         */

        private String member_id;
        @SerializedName("class")
        private List<ClassBean> classX;
        private List<GroupBean> group;
        private List<InterestAreaBean> interest_area;
        private List<MembershipBean> membership;

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public List<ClassBean> getClassX() {
            return classX;
        }

        public void setClassX(List<ClassBean> classX) {
            this.classX = classX;
        }

        public List<GroupBean> getGroup() {
            return group;
        }

        public void setGroup(List<GroupBean> group) {
            this.group = group;
        }

        public List<InterestAreaBean> getInterest_area() {
            return interest_area;
        }

        public void setInterest_area(List<InterestAreaBean> interest_area) {
            this.interest_area = interest_area;
        }

        public List<MembershipBean> getMembership() {
            return membership;
        }

        public void setMembership(List<MembershipBean> membership) {
            this.membership = membership;
        }

        public static class ClassBean {
            /**
             * id : 1
             * class_name : Aerobic
             */

            private String id;
            private String class_name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getClass_name() {
                return class_name;
            }

            public void setClass_name(String class_name) {
                this.class_name = class_name;
            }
        }

        public static class GroupBean {
            /**
             * id : 1
             * name : Aaa
             */

            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class InterestAreaBean {
            /**
             * id : 1
             * gym_id : 3
             * interest : Gym
             */

            private String id;
            private String gym_id;
            private String interest;

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

            public String getInterest() {
                return interest;
            }

            public void setInterest(String interest) {
                this.interest = interest;
            }
        }

        public static class MembershipBean {
            /**
             * id : 1
             * membership_label : membership demo
             */

            private String id;
            private String membership_label;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMembership_label() {
                return membership_label;
            }

            public void setMembership_label(String membership_label) {
                this.membership_label = membership_label;
            }
        }
    }
}
