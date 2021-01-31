package com.salinteam.emdadcustomerclub.payload;

import com.salinteam.emdadcustomerclub.model.GroupLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */

@Getter
@Setter
public class UserInfoResponse {

    public UserInfoResponse(GroupLevel groupLevel) {
        this.groupLevel = groupLevel;
    }

    private String phonenumber;

    private GroupLevel groupLevel;

    private Integer score;

    public UserInfoResponse(String phonenumber, GroupLevel groupLevel, Integer score) {
        this.phonenumber = phonenumber;
        this.groupLevel = groupLevel;
        this.score = score;
    }
}
