package com.ucamp.JM.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class follow {
    int follow_number;
    int follower;
    int followee;
}
