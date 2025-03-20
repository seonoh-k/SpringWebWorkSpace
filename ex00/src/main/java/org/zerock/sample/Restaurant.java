package org.zerock.sample;

import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
public class Restaurant {

    // 의존 주입
    // 1. 필드(변수) 주입
    // 2. Setter 주입
    // 3. 생성자 주입 : 생성자가 하나 있으면 자동으로 주입된다 [묵시적 자동 주입]
    // @Autowired

    //@Autowired
    @Setter(onMethod_ = @Autowired)
    private Chef chef; // - 빈
}
// 기존 방식 - 사용 안함
//Restaurant rs = new Restaurant();
//Chef cf = new Chef();
//rs.setChef(cf);
