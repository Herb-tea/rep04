package cn.itcast.web.download;

import cn.itcast.web.utils.DownLoadUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/downloadServlet")
public class DownloadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数，接收html文件中对应的文件名称
        String filename = request.getParameter("filename");
        //创建ServletContext对象
        ServletContext servletContext = this.getServletContext();
        //2.使用字节输入流读取文件（加载文件进内存）
        //找到文件服务器路径以便进行下一步字节输入流进行读取数据
        String realPath = servletContext.getRealPath("/img/" + filename);
        FileInputStream fis = new FileInputStream(realPath);

        /*没有以下步骤，则只会在浏览器中直接默认打开文件，而不是以附件形式打开*/
        //3.设置response的响应头
        //3.1设置响应头类型：content-type
        //现在的浏览器较智能，可省略此两行
        String mimeType = servletContext.getMimeType(filename);//获取文件的mime类型
        response.setHeader("content-type",mimeType);
        /*解决中文文件名问题*/
        //1.获取user-agent请求头、
        String agent = request.getHeader("user-agent");
        //2.使用工具类方法编码文件名即可
        filename = DownLoadUtils.getFileName(agent, filename);
        //3.2设置响应头打开方式:content-disposition
        //下载的关键步骤，attachment告诉浏览器以附件的形式来打开该文件，就是以下载的形式来保存文件
        //attachment表示附件，inline是默认值，浏览器能识别就自动打开，识别不了就下载
        //双引号中的filename与html中的相对应，filename表示：下载提示框展示的名称
        response.setHeader("content-disposition","attachment;filename="+filename);


        //4.作为响应的方式，服务器端向浏览器写数据（发送响应消息体）
        ServletOutputStream sos = response.getOutputStream();
        byte[] bytes = new byte[1024 * 8];
        int len = 0;
        while((len = fis.read(bytes)) != -1){
            sos.write(bytes,0,len);
        }
        //sos.close();tomcat会自动将其关闭（也与servlet的生命周期中执行的destroy方法释放资源有关）
        fis.close();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
