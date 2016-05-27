<!--%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%-->

<html>
<head>
  <title>Login</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <!-- ExtJS css -->
  <!--link rel="stylesheet" type="text/css" href="/resources/ext-3.2.1/resources/css/ext-all.css" /-->
  <!--link href="/css/main.css" rel="stylesheet" type="text/css"-->
  <!-- App js -->
  <!--script type="text/javascript" src="/resources/js/control-panel.js"></script-->
</head>
  <body>
    <form method="post" action="/add_message_to_chat" role="form">
      <table>
        <tr>
          <td colspan="2" style="color: red">Form to send your message</td>
        </tr>
        <tr>
          <td>Your nick:</td>
          <td><input type="text" name="nick" /></td>
        </tr>
        <tr>
          <td>Your message:</td>
          <td><input type="text" name="message" /></td>
        </tr>
        <tr>
          <td><input type="hidden" name="command" value="addMessageToChat"/></td>
        </tr>
        <tr>
          <td><input type="submit" value="send" /></td>
        </tr>
      </table>
    </form>
  </body>
</html>