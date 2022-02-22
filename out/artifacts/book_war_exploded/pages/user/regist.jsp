<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>尚硅谷会员注册页面</title>

    <%-- 静态包含，base 标签、引入的 css 和 jquery 文件 --%>
    <%@ include file="/pages/common/head.jsp" %>

    <script type="text/javascript">

        // 网页全部加载完之后，会执行里面的操作
        $(function () {

            // 最好写在所有 script 事件之前
            // 给用户名输入框（id：username）绑定失去焦点事件
            $("#username").blur(function () {
                // 获取用户名
                var username = this.value;

                /**
                 * 发送 ajax 请求，验证用户名是否可用
                 * 请求类型为：GET，返回的数据类型为：JSON
                 * 参数1：url：请求的 url 地址
                 * 参数2：data：发送给服务器的数据（url参数）
                 *      发送的数据为：?action=ajaxExistsUsername&username=用户的输入
                 * 参数3：callback：成功的回调函数
                 */
                $.getJSON("${basePath}userServlet","action=ajaxExistsUsername&username=" + username,function (data) {
                    // 接收的数据是：existsUsername作为key，existsUsername的值作为value
                    // value为布尔型，返回 true 表示用户名已存在，返回 false 表示用户名可用
                    if(data.existsUsername){
                        $("span.errorMsg").text("用户名已存在！");
                    }else{
                        $("span.errorMsg").text("用户名可用！");
                    }
                });
            });

            // 给注册按钮绑定单击事件
            $("#sub_btn").click(function () {
                // 验证用户名：必须由字母，数字下划线组成，并且长度为 5 到 12 位
                // 1、获取用户名输入框里的内容
                var usernameText = $("#username").val();
                // 2、创建正则表达式对象
                // ^：开始；$：结尾；\w：字母、数字或下划线；{5，12}：5-12位
                var usernamePatt = /^\w{5,12}$/;
                // 3、使用test方法验证是否合法
                if (!usernamePatt.test(usernameText)) {
                    // 4、提示用户结果
                    $("span.errorMsg").text("用户名不合法！");
                    // 禁止跳转
                    return false;
                } else {
                    $("span.errorMsg").text("");
                }

                // 验证密码：必须由字母，数字下划线组成，并且长度为 5 到 12 位
                // 1、获取密码输入框里的内容
                var passwordText = $("#password").val();
                // 2、创建正则表达式对象
                // ^：开始；$：结尾；\w：字母、数字或下划线；{5，12}：5-12位
                var passwordPatt = /^\w{5,12}$/;
                // 3、使用test方法验证
                if (!passwordPatt.test(passwordText)) {
                    // 4、提示用户结果
                    $("span.errorMsg").text("密码不合法！");
                    // 禁止跳转
                    return false;
                }

                // 验证确认密码：和密码相同
                // 1、获取确认密码
                var repwdText = $("#repwd").val();
                // 2、和密码相比较
                if (passwordText != repwdText) {
                    // 3、提示用户结果
                    $("span.errorMsg").text("两次输入的密码不同！");
                    // 禁止跳转
                    return false;
                }

                // 邮箱验证：xxxxx@xxx.com
                // 1、获取邮箱里的内容
                var emailText = $("#email").val();
                // 2、创建正则表达式对象
                var emailPatt = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
                // 3、使用test方法验证是否合法
                if (!emailPatt.test(emailText)) {
                    // 4、提示用户结果
                    $("span.errorMsg").text("邮箱格式不合法！");
                    // 禁止跳转
                    return false;
                }

                // 验证码：现在只需要验证用户已输入。因为还没讲到服务器。验证码生成。
                // 1、获取验证码里的内容
                var codeText = $("#code").val();
                // 2、去掉验证码（codeText）前后的空格
                codeText = $.trim(codeText);
                // 3、验证验证码输入框是否为空
                if (codeText == null || codeText == "") {
                    // 3、提示用户结果
                    $("span.errorMsg").text("验证码不能为空");
                    // 禁止跳转
                    return false;
                }

                // 所有验证都没问题，不提示
                $("span.errorMsg").text("");

            });

            // 给验证码的图片，绑定单击事件
            $("code_img").click()(function () {
                // 在事件响应的 function 函数中有一个 this 对象。这个 this 对象，是当前正在响应事件的 dom 对象
                // src 属性表示验证码 img 标签的 图片路径。它可读，可写
                // basePath：工程路径
                // new Date()：获取时间戳，即每次点击的链接都不一样，就没有了浏览器缓存的影响
                this.src = "${basePath}kaptcha.jpg?d=" + new Date();
            });



        });

    </script>

    <style type="text/css">
        .login_form {
            height: 420px;
            margin-top: 25px;
        }
    </style>
</head>

<body>
<div id="login_header">
    月海.jpg
</div>

<div class="login_banner">

    <div id="l_content">
        <span class="login_word">欢迎注册</span>
    </div>

    <div id="content">
        <div class="login_form">
            <div class="login_box">
                <div class="tit">
                    <h1>注册尚硅谷会员</h1>
                    <%-- 判断 Request 域中的 msg 有没有数据，
                    没有的话不显示任何值；有显示 msg 的值 --%>
                    <span class="errorMsg">${ msg }</span>
                </div>
                <div class="form">
                    <form action="userServlet" method="post">
                        <%-- 隐藏域，表明该表单为 注册请求 --%>
                        <input type="hidden" name="action" value="regist" />
                        <label>用户名称：</label>
                        <%-- 判断 Request 域中的 username 有没有数据，
                        没有的话不显示任何值；有显示 username 的值 --%>
                        <input class="itxt" type="text" placeholder="请输入用户名"
                               autocomplete="off" tabindex="1" name="username" id="username"
                               value="${ username }"/>
                        <br/>
                        <br/>
                        <label>用户密码：</label>
                        <input class="itxt" type="password" placeholder="请输入密码" autocomplete="off" tabindex="1"
                               name="password" id="password"/>
                        <br/>
                        <br/>
                        <label>确认密码：</label>
                        <input class="itxt" type="password" placeholder="确认密码" autocomplete="off" tabindex="1"
                               name="repwd" id="repwd"/>
                        <br/>
                        <br/>
                        <label>电子邮件：</label>
                        <%-- 判断 Request 域中的 email 有没有数据，
                        没有的话不显示任何值；有显示 email 的值 --%>
                        <input class="itxt" type="text" placeholder="请输入邮箱地址"
                               autocomplete="off" tabindex="1" name="email" id="email"
                               value="${ email }"/>
                        <br/>
                        <br/>
                        <label>验 &nbsp;证 码：</label>
                        <input class="itxt" type="text" name="code" style="width: 100px;" id="code" />
                        <%-- kaptcha.jpg 为 web.xml 中配置的 servlet，访问该 servlet 就会生成验证码 --%>
                        <!-- 且该验证码还会保存到 session 域中 -->
                        <img id="code_img" src="kaptcha.jpg" alt="" style="float:right;margin-right:40px;width:130px;height:40px"> <br/>
                        <br/>
                        <input type="submit" value="注册" id="sub_btn"/>

                    </form>
                </div>

            </div>
        </div>
    </div>
</div>

<%-- 静态包含，页尾 --%>
<%@include file="/pages/common/footer.jsp" %>

</body>

</html>