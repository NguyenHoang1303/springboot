package com.product.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profiles")
public class Profile {

    @Id
    @Column(name = "account_id")
    private Long accountId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id", insertable = false, updatable = false)
    private Account account;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;


    public static final class ProfileBuilder {
        private String name;
        private String phone;
        private String email;
        private String address;

        private ProfileBuilder() {
        }

        public static ProfileBuilder aProfile() {
            return new ProfileBuilder();
        }

        public ProfileBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ProfileBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public ProfileBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public ProfileBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public Profile build() {
            Profile profile = new Profile();
            profile.setName(name);
            profile.setPhone(phone);
            profile.setEmail(email);
            profile.setAddress(address);
            return profile;
        }
    }
}
