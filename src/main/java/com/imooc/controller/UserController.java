package com.imooc.controller;

import com.alibaba.fastjson.JSONObject;
import com.imooc.VO.ResultVO;
import com.imooc.VO.UserVO;
import com.imooc.config.UserAppInteface;
import com.imooc.dataobject.User;
import com.imooc.dataobject.UserInfo;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.form.UserForm;
import com.imooc.form.UserInfoForm;
import com.imooc.form.VerifyForm;
import com.imooc.param.PersonParam;
import com.imooc.repository.UserInfoRepository;
import com.imooc.repository.UserRepository;
import com.imooc.utils.ConvertUtils;
import com.imooc.utils.HttpClientUtil;
import com.imooc.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 用户相关
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    /**
     * 用户注册
     * @param userForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/register")
    public ResultVO create(@Valid UserForm userForm,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("参数不正确, userForm={}", userForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        User userOld = userRepository.findByOpenid(userForm.getOpenid());
        User user = new User();
        if (userOld != null) {
            user.setId(userOld.getId());
        }
        user.setUsername(userForm.getUsername());
        user.setOpenid(userForm.getOpenid());
        user.setPhone(userForm.getPhone());
        user.setSex(userForm.getSex());

        return ResultVOUtil.success(userRepository.save(user));
    }

    /**
     * 查询用户所有信息
     * @param openid
     * @return
     */
    @GetMapping("/getUserInfo")
    public ResultVO getUserInfo(@RequestParam("openid") String openid) {
        User user = userRepository.findByOpenid(openid);
        return ResultVOUtil.success(user);
    }

    /**
     *
     * @return
     */
    @GetMapping("/{id}")
    public ResultVO getUser( @PathVariable String id) {
        UserInfo userInfo = userInfoRepository.findByUserId(id);
        User user = userRepository.findById(Integer.valueOf(id));
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        if(user != null){
            userVO.setUserInfo(userInfo);
        }

        return ResultVOUtil.success(userVO);
    }

    /**
     * 实名认证
     * @param userForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/verify")
    public ResultVO create(@Valid VerifyForm userForm,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("参数不正确, userForm={}", userForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        User user = new User();
        return ResultVOUtil.success(userRepository.save(user));
    }

    /**
     * 用户查询分页
     * @param param
     * @param page
     * @param size
     * @param sorts
     * @return
     */
    @PostMapping("/pagination")
    public ResultVO pagination(@RequestBody PersonParam param, @RequestParam("page") Integer page, @RequestParam("size") Integer size,
                               @RequestParam("sort") String sorts) {
        Pageable pageable = ConvertUtils.pagingConvert(page,size,sorts);
        return ResultVOUtil.success(null);
    }

    /**
     * 个人资料完善
     * @param userInfoForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/info")
    public ResultVO data(@Valid UserInfoForm userInfoForm,
                         BindingResult bindingResult) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoForm, userInfo);
        Date time = new Date();
        userInfo.setId(UUID.randomUUID().toString());
        userInfo.setUpdTime(time);
        userInfo.setUpdTime(time);
        return ResultVOUtil.success(userInfoRepository.save(userInfo));
    }

    /**
     * 上传生活照片
     * @param files
     * @param userId
     * @return
     * @throws IOException
     */
    @PostMapping("/life/pictures")
    public ResultVO pictures(@RequestPart MultipartFile [] files,@RequestParam("userId") String userId) throws IOException {
        List<String> picturesPath = new ArrayList<>();
        for(MultipartFile file:files){
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
            String picId = UUID.randomUUID().toString();
            String fileName = "D:/pictures/" + picId+"."+suffix;
            File destFile = new File(fileName);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            file.transferTo(destFile);
            picturesPath.add(fileName);
        }
        UserInfo userInfo = userInfoRepository.findByUserId(userId);
        userInfo.setLifePhotos(picturesPath.toString());
        return ResultVOUtil.success(userInfoRepository.save(userInfo));
    }


    @PostMapping("/login")
    public ResultVO user_login(
            @RequestParam("code") String code,
            @RequestParam("userHead") String head,
            @RequestParam("userName") String username,
            @RequestParam("userGender") String gender,
            @RequestParam("userCity") String userCity,
            @RequestParam("userProvince") String userProvince
    ){
        // 配置请求参数
        Map<String, String> param = new HashMap<>();
        param.put("appid", UserAppInteface.PROGRAM_ID);
        param.put("secret", UserAppInteface.PROGRAM_SECRET);
        param.put("js_code", code);
        param.put("grant_type", UserAppInteface.WX_LOGIN_GRANT_TYPE);
        // 发送请求
        String wxResult = HttpClientUtil.doGet(UserAppInteface.WX_LOGIN_URL, param);
        JSONObject jsonObject = JSONObject.parseObject(wxResult);
        // 获取参数返回的
        String session_key = jsonObject.get("session_key").toString();
        String open_id = jsonObject.get("openid").toString();
        // 根据返回的user实体类，判断用户是否是新用户，不是的话，更新最新登录时间，是的话，将用户信息存到数据库
        User user = userRepository.findByOpenid(open_id);
        if(user != null){
            Date time = new Date();
            user.setLatestLoginTime(time);
            userRepository.save(user);
        }else{
            User insert_user = new User();
            insert_user.setHead(head);
            insert_user.setUsername(username);
            insert_user.setSex(Integer.valueOf(gender));
            insert_user.setLatestLoginTime(new Date());
            insert_user.setCity(userCity);
            insert_user.setProvince(userProvince);
            insert_user.setOpenid(open_id);
            user.setLatestLoginTime(new Date());
            System.out.println("insert_user:"+insert_user.toString());
            // 添加到数据库
            userRepository.save(insert_user);
        }
        // 封装返回小程序
        Map<String, String> result = new HashMap<>();
        result.put("open_id", open_id);
        return ResultVOUtil.success(open_id);
    }
}
