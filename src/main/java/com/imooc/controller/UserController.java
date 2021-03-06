package com.imooc.controller;

import com.alibaba.fastjson.JSONObject;
import com.imooc.VO.ResultVO;
import com.imooc.VO.UserVO;
import com.imooc.config.JwtSecurityProperties;
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
import com.imooc.service.UserService;
import com.imooc.utils.*;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    private JwtSecurityProperties jwtSecurityProperties;
    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userService;
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;

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
    @GetMapping("/getUser")
    public ResultVO getUser( @RequestParam String openId) {
        UserInfo userInfo = userInfoRepository.findByOpenId(openId);
        User user = userRepository.findByOpenid(openId);
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
        IdCardVerify.IdentityCardVerification(userForm.getIdCard());
        UserInfo userInfo = userInfoRepository.findByOpenId(userForm.getOpenId());
        userInfo.setIdCard(userForm.getIdCard());
        userInfo.setRealName(userForm.getRealName());
        userInfo.setIsRealNameValid(1);
        return ResultVOUtil.success( userInfoRepository.save(userInfo));
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

        Page<UserInfo> userspage = userService.pageination(param,pageable);
        return ResultVOUtil.success(userspage);
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
        User user = userRepository.findByOpenid(userInfoForm.getOpenId());
        if(user == null){
            throw new SellException(ResultEnum.USER_NOT_EXIST);
        }
        UserInfo userInfo = userInfoRepository.findByOpenId(userInfoForm.getOpenId());
        if(userInfo == null){

            userInfo = new UserInfo();
            userInfo.setId(UUID.randomUUID().toString());
            BeanUtils.copyProperties(userInfoForm, userInfo);
            userInfo.setBirthDate(DateUtils.parseDate(userInfoForm.getBirthDate(),"yyyy-MM-dd"));
            Date time = new Date();
            userInfo.setCreateTime(time);
            userInfo.setUpdTime(time);
        }else{
            BeanUtils.copyProperties(userInfoForm, userInfo);
            Date time = new Date();
            userInfo.setUpdTime(time);
        }

        return ResultVOUtil.success(userInfoRepository.save(userInfo));
    }

    /**
     * 上传生活照片
     * @param files
     * @return
     * @throws IOException
     */
    @PostMapping("/life/pictures")
    public ResultVO pictures(@RequestPart MultipartFile [] files,@RequestParam("openId") String openId) throws IOException {
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
        UserInfo userInfo = userInfoRepository.findByOpenId(openId);
        userInfo.setLifePhotos(picturesPath.toString());
        return ResultVOUtil.success(userInfoRepository.save(userInfo));
    }


    @PostMapping("/login")
    public ResultVO user_login(
            @RequestParam("code") String code,
//            @RequestParam("userHead") String head,
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
            user = new User();
            user.setUsername(username);
            user.setSex(Integer.valueOf(gender));
            user.setLatestLoginTime(new Date());
            user.setCity(userCity);
            user.setProvince(userProvince);
            user.setOpenid(open_id);
            user.setLatestLoginTime(new Date());
            // 添加到数据库
            userRepository.save(user);
        }
        // 封装返回小程序
        Map<String, String> result = new HashMap<>();
        result.put("open_id", open_id);
        //创建token
        Map<String, Object> claims = new HashMap<>(16);
        claims.put("roles", "user");
        String token = jwtTokenUtils.createToken(open_id, claims);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        userVO.setToken(jwtSecurityProperties.getTokenStartWith() + token);
        return ResultVOUtil.success(userVO);
    }
}
