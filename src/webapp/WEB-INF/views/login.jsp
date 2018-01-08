<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <style>
        #err {
            color: red;
        }
        .login-page {
            width: 360px;
            padding: 8% 0 0;
            margin: auto;
        }
        .form {
            position: relative;
            z-index: 1;
            background: #FFFFFF;
            max-width: 360px;
            margin: 0 auto 100px;
            padding: 25px;
            text-align: center;
            box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);
        }
        .form input {
            font-family: "Roboto", sans-serif;
            outline: 0;
            background: #f2f2f2;
            width: 100%;
            border: 0;
            margin: 0 0 5px;
            padding: 15px;
            box-sizing: border-box;
            font-size: 14px;
        }
        .form button {
            font-family: "Roboto", sans-serif;
            text-transform: uppercase;
            outline: 0;
            background: #4d88af;
            width: 100%;
            border: 0;
            padding: 15px;
            color: #FFFFFF;
            font-size: 14px;
            -webkit-transition: all;
            transition: all;
            cursor: pointer;
        }
        .form button:hover,.form button:active,.form button:focus {
            background: #4d88af;
        }
        .form .message a {
            color: #4d88af;
            text-decoration: none;
        }
        .container .info h1 {
            margin: 0 0 15px;
            padding: 0;
            font-size: 36px;
            font-weight: 300;
            color: #1a1a1a;
        }
        .container .info span {
            color: #4d4d4d;
            font-size: 12px;
        }
        .container .info span a {
            color: #000000;
            text-decoration: none;
        }
        body {
            background: #4d88af;
            background: -webkit-linear-gradient(right, #4d88af, #4d88af);
            background: -moz-linear-gradient(right, #4d88af, #4d88af);
            background: -o-linear-gradient(right, #4d88af, #4d88af);
            background: linear-gradient(to left, #4d88af, #4d88af);
            font-family: "Roboto", sans-serif;
            -webkit-font-smoothing: antialiased;
            -moz-osx-font-smoothing: grayscale;
        }
    </style>
    <title>Login</title>
</head>
<body>
<c:url value="/login" var="loginUrl"/>
    <div class="login-page">
        <div class="form">
        <form class="login-form" action="${loginUrl}" method="post">
            <input type="text" placeholder="username" id="username" name="username"/>
            <input type="password" placeholder="password" id="password" name="password"/>
            <button type="submit" class="btn">Log in</button>
        </form>
            <c:if test="${param.error != null}">
                <p id="err">
                    Invalid username or password
                </p>
            </c:if>
            <c:if test="${param.logout != null}">
                <p>
                    You have been logged out
                </p>
            </c:if>
        </div>
    </div>
</form>
</body>
</html>