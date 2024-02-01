package com.ssafy.backend.mokkoji.service;

import com.ssafy.backend.alarm.model.dto.ReqestAlarmDTO;
import com.ssafy.backend.alarm.service.AlarmService;
import com.ssafy.backend.category.model.domain.Category;
import com.ssafy.backend.category.model.dto.CategoryDto;
import com.ssafy.backend.category.service.CategoryService;
import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import com.ssafy.backend.mokkoji.model.domain.MokkojiRankings;
import com.ssafy.backend.mokkoji.model.dto.*;
import com.ssafy.backend.mokkoji.model.vo.MokkojiDetailVO;
import com.ssafy.backend.mokkoji.model.vo.MokkojiListVO;
import com.ssafy.backend.mokkoji.model.vo.MokkojiRankingsVO;
import com.ssafy.backend.user.model.domain.User;
import com.ssafy.backend.user.model.vo.UserInformationVO;
import com.ssafy.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ssafy.backend.common.response.BaseResponseStatus.*;

//각 서비스를 끌어다 쓰기 위해서 퍼사드 형태로 모듈 분리
//하나의 서비스에는 하나의 레포만 존재하도록 설정 -> 비지니스 로직 처리
@RequiredArgsConstructor
@Service
@Slf4j
public class MokkojiFacade {
    private final int CREATE_MOKKOJI_POINT = 5000;
    private final CategoryService categoryService;
    private final MokkojiRankingService mokkojiRankingService;
    private final MokkojiService mokkojiService;
    private final MokkojiCategoryService mokkojiCategoryService;
    private final UserService userService;
    private final AlarmService alarmService;

    @Transactional(rollbackFor = Exception.class)
    public void saveMokkoji(MokkojiCreateRequestDto dto){

        User user = userService.canAddMokkoji(dto.getLeaderId(), CREATE_MOKKOJI_POINT);
        Mokkoji mokkoji = mokkojiService.addMokkoji(dto.toEntity());
        List<Category> categories = categoryService.getCategories(dto.getMokkojiCategories());
        //이거 나중에 saveAll로 바꿔야함
        for (Category category : categories) {
            mokkojiCategoryService.addMokkjiCategory(mokkoji, category);
        }
        userService.modifyMokkojiId(user, mokkoji);
    }


    public MokkojiRankingsVO getByMokkojiNameRanking(String mokkojiName) {
        List<MokkojiRankings> byMokkojiName = mokkojiRankingService.getByMokkojiName(mokkojiName);
        log.info("모꼬지 이름 랭킹 검색입니다.{}",byMokkojiName);
        MokkojiRankings mokkojiRankings = byMokkojiName.get(0);

        MokkojiRankDto mokkojiDto = new MokkojiRankDto(mokkojiRankings);
        List<Category> categoriesEntities = categoryService.getCategories(mokkojiRankings.getCategories());

        List<CategoryDto> categories = categoriesEntities
                .stream().map(CategoryDto::new)
                .collect(Collectors.toList());
        return new MokkojiRankingsVO(categories, mokkojiDto);
    }

    public List<MokkojiRankingsVO> geTmokkojiTopTen() {
        List<MokkojiRankingsVO> list = new ArrayList<>();
        List<MokkojiRankings> topTen = mokkojiRankingService.getRankingTopTen();
        log.info("모꼬지 이름 탑텐입니다.{}",topTen);
        for (MokkojiRankings mokkojiRankings: topTen) {
            MokkojiRankDto mokkojiDto = new MokkojiRankDto(mokkojiRankings);
            List<Category> categoriesEntities = categoryService.getCategories(mokkojiRankings.getCategories());
            List<CategoryDto> categories = categoriesEntities
                    .stream().map(CategoryDto::new)
                    .collect(Collectors.toList());
            list.add(new MokkojiRankingsVO(categories, mokkojiDto));
        }
        return list;

    }


    public MokkojiListVO getMokkojiList(List<Integer> categories, int page, String keyword) {
        Page<Mokkoji> mokkojiList;
        if(categories == null || categories.size() == 0){
            mokkojiList = mokkojiService.getMokkojiList(page, keyword);
            log.info("모꼬지 목록조회 카테고리가 없을 때 입니다. {}", mokkojiList);
        }
        else{
            mokkojiList = categoryService.getMokkojiList(categories, page, keyword);
            log.info("모꼬지 목록조회 카테고리가 있을 때 입니다. {}", mokkojiList);
        }
        log.info("전체 아이템 수: {}", mokkojiList.getTotalElements());
        log.info("전체 페이지 수: {}", mokkojiList.getTotalPages());
        Map<Mokkoji, List<Category>> map = mokkojiCategoryService.getMokkojis(mokkojiList);

        List<MokkojiCategoryDto> list = map.entrySet().stream().map(e -> {
            return new MokkojiCategoryDto(e.getValue(), e.getKey());
        }).collect(Collectors.toList());

        return new MokkojiListVO(list, mokkojiList.getTotalPages());
    }
    //모꼬지 삭제로직
    @Transactional
    public void deleteMokkoji(String userId) {
        User user = userService.deleteMokkojiCheck(userId);
        Mokkoji mokkoji = user.getMokkojiId();
        mokkojiCategoryService.deleteMokkojiCategory(mokkoji);
        userService.deleteMokkojiUser(mokkoji);
        mokkojiService.deleteMokkoji(mokkoji);
    }

    @Transactional
    public void kickUser(String leader, String member) {
        User user = userService.leaderCheck(leader);
        User memberCheck = userService.memberCheck(member, user.getMokkojiId());
        userService.kickMokkojiUser(memberCheck);
    }

    @Transactional
    public void leaveMokkoji(String userId) {
        User user = userService.isExistUser(userId);
        if(user.getMokkojiId() == null) throw new BaseException(NOT_EXIST_USER_MOKKOJI);
        if(user.getMokkojiId().getLeaderId().equals(user.getUserId()))
            throw new BaseException(NOT_LEAVE_MOKKOJI_LEADER);
        userService.kickMokkojiUser(user);
    }

    public MokkojiDetailVO getDetailMokkoji(int mokkojiId, String userId) {
        MokkojiDetailVO dto = new MokkojiDetailVO();
        Mokkoji mokkoji = mokkojiService.getMokkojiById(mokkojiId);
        List<UserInformationVO> user = userService.getUserInformationByMokkoji(mokkoji);
        List<Category> categories = mokkojiCategoryService.getCategories(mokkoji);
        if("".equals(userId) || userId == null) {
            dto.setUserId("");
            dto.setMyMokkojiId(0);
        }
        else{
            User existUser = userService.isExistUser(userId);
            if(existUser.getMokkojiId() == null){
                dto.setMyMokkojiId(0);
            }else{
                dto.setMyMokkojiId(existUser.getMokkojiId().getMokkojiId());
            }
            dto.setUserId(userId);
        }
        if(userId != null && userId.equals(mokkoji.getLeaderId())) dto.setLeader(true);
        dto.setMokkojiData(mokkoji, user, categories);
        return dto;
    }

    @Transactional
    public void applyForMokkoji(MokkojiApplyForRequestDto dto) {
        User user = userService.isExistUser(dto.getUserId());
        if(user.getMokkojiId() != null)
            throw new BaseException(ALREADY_EXIST_USER_MOKKOJI);
        Mokkoji mokkoji = mokkojiService.getMokkojiById(dto.getMokkojiId());
        ReqestAlarmDTO alarmDto = ReqestAlarmDTO.builder()
                .tagId(2)
                .userId(user.getUserId())
                .requestedUserId(mokkoji.getLeaderId())
                .build();
        alarmService.aVoidDuplicateAlaram(alarmDto);
        alarmService.requestAlarm(alarmDto);
    }


    @Transactional
    public void acceptForMokkoji(String leaderId, String memberId) {

        ///leader check , member check
        User leader = userService.leaderCheck(leaderId);
        User member = userService.isExistUser(memberId);
        if(member.getMokkojiId() != null)
            throw new BaseException(ALREADY_EXIST_USER_MOKKOJI);
        //save
        userService.modifyMokkojiId(member, leader.getMokkojiId());

        //alarm save
        ReqestAlarmDTO alarmDto = ReqestAlarmDTO.builder()
                .tagId(3)
                .userId(member.getUserId())
                .requestedUserId(leader.getUserId())
                .build();
        alarmService.requestAlarm(alarmDto);
    }

    public void deleteAlarm(String leaderId, String memberId) {
        ReqestAlarmDTO alarmDto = ReqestAlarmDTO.builder()
                .tagId(2)
                .userId(memberId)
                .requestedUserId(leaderId)
                .build();
        alarmService.deleteAlarm(alarmDto);
    }
}
