package org.firas.user.model;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.MappedSuperclass;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.firas.common.helper.TrueValueHelper;
import org.firas.common.model.IdModel;

@MappedSuperclass
@NoArgsConstructor
public class UserBase extends IdModel {

    public UserBase(String userName) {
        this.userName = userName;
    }

    @Override
    public HashMap<String, Object> toMap(Map<String, Object> options) {
        HashMap<String, Object> result = super.toMap(options);
        if (TrueValueHelper.isTrue(options, "user_name")) {
            result.put("user_name", this.getUserName());
        }
        if (TrueValueHelper.isTrue(options, "name")) {
            result.put("name", this.getName());
        }
        if (TrueValueHelper.isTrue(options, "mobile")) {
            result.put("mobile", this.getMobile());
        }
        if (TrueValueHelper.isTrue(options, "email")) {
            result.put("email", this.getEmail());
        }
        if (TrueValueHelper.isTrue(options, "nick")) {
            result.put("nick", this.getNick());
        }
        if (TrueValueHelper.isTrue(options, "gender")) {
            result.put("gender", this.getGender());
        }
        return result;
    }


    @Column(name = "user_name", unique = true, nullable = false, length = 64)
    @Getter @Setter private String userName;

    public static final int NAME_MIN_LENGTH = 2, NAME_MAX_LENGTH = 64;
    @Column(length = NAME_MAX_LENGTH)
    @Getter @Setter private String name;

    @Column(length = 64)
    @Getter @Setter private String nick;

    public static enum Gender {
        UNKNOWN((byte)0),
        MALE((byte)1),
        FEMALE((byte)2);

        private byte value;
        private Gender(byte value) {
            this.value = value;
        }
    }

    @Enumerated(EnumType.ORDINAL)
    @Getter @Setter private Gender gender;
    public Boolean isMale() {
        return gender.equals(Gender.MALE);
    }
    public Boolean isFemale() {
        return gender.equals(Gender.FEMALE);
    }
	public static Gender parseGender(String gender) {
		if (null == gender) return Gender.UNKNOWN;
		if (gender.equals("男") || gender.equalsIgnoreCase("male") ||
                gender.equalsIgnoreCase("m")) {
            return Gender.MALE;
        }
		if (gender.equals("女") || gender.equalsIgnoreCase("female") ||
                gender.equalsIgnoreCase("f")) {
            return Gender.FEMALE;
        }
		return Gender.UNKNOWN;
    }
    public UserBase setGenderWithString(String gender) {
        this.gender = parseGender(gender);
        return this;
    }

    @Column(length = 32)
    @Getter @Setter private String mobile;

    @Column(length = 64)
    @Getter @Setter private String email;
}
