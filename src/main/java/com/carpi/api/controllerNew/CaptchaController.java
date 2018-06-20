package com.carpi.api.controllerNew;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

@Controller
@RequestMapping("captcha/")
public class CaptchaController {

    @Resource
    private Producer captchaProducer;
    /**
     *             
     *                获取验证码图片
     * @author         ccg
     * @param         request
     * @param         response
     * @return
     * @throws         IOException
     * Created        2017年1月17日 下午5:07:28
     */
    @RequestMapping(value = "getCaptchaCode", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ModelAndView getCaptchaCode(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();
        
        response.setDateHeader("Expires", 0);  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        response.setHeader("Pragma", "no-cache");  
        response.setContentType("image/jpeg"); 
        
        //生成验证码文本
        String capText = captchaProducer.createText();  
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        System.out.println("生成验证码文本===="+capText);
        //利用生成的字符串构建图片
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();  
        ImageIO.write(bi, "jpg", out);  
        
        try {  
            out.flush();  
        } finally {  
            out.close();  
        }
        return null;
    }
    
    /**
     *             
     *                前端输入的验证码与生成的对比
     * @author         ccg
     * @param         request
     * @param         response
     * @param         captchaCode
     * Created        2017年1月17日 下午5:34:23
     */
    @RequestMapping("checkCaptchaCode")
    public void checkCaptchaCode(HttpServletRequest request, HttpServletResponse response,@RequestParam("captchaCode") String captchaCode){
        System.out.println("页面输入验证码===="+captchaCode);
        
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        
        String generateCode =(String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String result = "";
        if(generateCode.equals(captchaCode)){
            result = "验证成功";
        }else{
            result = "输入错误";
        }
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.print(result);
        out.flush();
    }
}
