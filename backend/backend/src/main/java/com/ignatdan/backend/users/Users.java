    package com.ignatdan.backend.users;

    import jakarta.persistence.*;

    @Entity
    @Table(name = "users")
    public class Users {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer userId;

        @Column(name = "username")
        private String username;

        @Column(name = "hash_password")
        private String hashPassword;

        @Column(name = "first_name")
        private String firstName;

        @Column(name = "last_name")
        private String lastName;

        public Integer getUser_id() {  // Updated getter
            return userId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getHashPassword() {
            return hashPassword;
        }

        public void setHashPassword(String hashPassword) {
            this.hashPassword = hashPassword;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
