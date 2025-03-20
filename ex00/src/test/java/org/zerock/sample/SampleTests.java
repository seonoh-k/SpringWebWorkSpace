package org.zerock.sample;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class) // 메인 함수 작성 없이 테스트 가능
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml") // 스프링 컨테이너 경로 지정
@Log4j // 로그 출력
public class SampleTests {

    // 자동 의존 주입
    @Setter(onMethod_ = @Autowired)
    private Restaurant restaurant;

    @Test // 테스트 메소드 지정
    public void testExit() {

        assertNotNull(restaurant);

        log.info(restaurant);
        log.info("=========");
        log.info(restaurant.getChef());

    }
}
