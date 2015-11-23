package com.zjnu.bike.test.domain;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.zjnu.bike.Application;
import com.zjnu.bike.domain.FileInfo;
import com.zjnu.bike.domain.PlayGuide;
import com.zjnu.bike.domain.User;
import com.zjnu.bike.enums.StatusEnum;
import com.zjnu.bike.repository.FileInfoRepository;
import com.zjnu.bike.repository.PlayGuideRepository;
import com.zjnu.bike.repository.UserRepository;
import com.zjnu.bike.util.RandUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据库数据模拟
 * @author ChenTao
 * @date 2015年11月19日下午9:42:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Slf4j
public class PlayGuideTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FileInfoRepository fileInfoRepository;
	
	@Autowired
	private PlayGuideRepository playGuideRepository;

	@Test
	public void save() throws Exception {
		System.out.println("---------------save----------------");
		List<PlayGuide> cList = new ArrayList<PlayGuide>();
		PageRequest pageRequest = new PageRequest(0, 10);
		List<User> uList = this.userRepository.findAll(pageRequest).getContent();
		for (User u : uList) {
			log.debug("{}", u);
		}
		PageRequest pageRequest2 = new PageRequest(0, 20);
		List<FileInfo> fList = this.fileInfoRepository.findAll(pageRequest2).getContent();
		for (FileInfo f : fList) {
			log.debug("{}", f);
		}
		for (int i = 0; i < 100; i++) {
			PlayGuide m = new PlayGuide();
			m.setCreateTime(RandUtil.getDate());
			m.setContent(RandUtil.getStringChineseRange(8, 100));
			m.setStatus(RandUtil.getEnum(StatusEnum.values()));
			m.setTitle(RandUtil.getStringChineseRange(3, 6));
			m.setOperator(RandUtil.getObject(uList));
			m.setImages(RandUtil.getList(fList, 3));
			cList.add(m);
		}
		this.playGuideRepository.save(cList);
		System.out.println("-------------------------------");
	}

	@Test
	public void findAll() throws Exception {
		System.out.println("--------------findAll-----------------");
		for (PlayGuide model : this.playGuideRepository.findAll()) {
			log.debug("{}", model);
		}
		System.out.println("-------------------------------");
	}
}
