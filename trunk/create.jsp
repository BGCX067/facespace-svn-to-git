<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>FaceSpace | Create FaceSpace Account</title>
<link type="text/css" href="style.css" rel="stylesheet"/>
</head>
<body>
  <div id="page">
    <div id="panel">
      <div id="logo">
        <img src="logo.gif" />
      </div>
      <div id="stuff">
        Please enter appropriate data into all the fields.
      </div>
    </div>
    <div id="main">

      <div id="content">

        <h1>Create FaceSpace Account</h1>

      <form action="process.jsp" method="post">
       <div class="subContent">
        <div class="formRow">
          <label class="login_lbl" for="email">Email:</label>
          <input id="email" name="email" type="text" />
        </div>
        <div class="formRow">
          <label class="login_lbl" for="password">Password:</label>
          <input id="password" name="password" type="password" />
        </div>
        <div class="spacer"></div>

        <div class="formRow">
          <label class="login_lbl" for="firstNme">First Name:</label>
          <input id="firstName" name="firstName" type="text" />
        </div>
        <div class="formRow">
          <label class="login_lbl" for="middleName">Middle Name:</label>
          <input id="middleName" name="middleName" type="text" />
        </div>
        <div class="formRow">
          <label class="login_lbl" for="lastName">Last Name:</label>
          <input id="lastName" name="lastName" type="text" />
        </div>
        <div class="spacer"></div>
        
        <div class="formRow">
          <label class="login_lbl" for="birthDay">Birth Day: (mm/dd/yyyy)</label>
          <input id="birthDay" name="birthDay" type="text" />
        </div>
        <div class="spacer"></div>
        
        <div class="formRow">
          <label class="login_lbl" for="gender">Gender:</label>
          <select id="gender" name="gender">
          <option value="m">Male</option>
          <option value="f">Female</option>
          </select>
        </div>
        
        <div class="spacer"></div>
        <div class="formRow">
          <label class="login_lbl" for="homePhone">Home Phone:</label>
          <input id="homePhone" name="homePhone" type="text" />
        </div>
        <div class="formRow">
          <label class="login_lbl" for="cellPhone">Cell Phone:</label>
          <input id="cellPhone" name="cellPhone" type="text" />
        </div>
        <div class="spacer"></div>
        <div class="formRow">
          <label class="login_lbl" for="streetAddress">Address:</label>
          <input id="streetAddress" name="streetAddress" type="text" />
        </div>
        <div class="formRow">
          <label class="login_lbl" for="city">City:</label>
          <input id="city" name="city" type="text" />
        </div>
        <div class="formRow">
          <label class="login_lbl" for="state">State:</label>
          <input id="state" name="state" type="text" />
        </div>
        <div class="formRow">
          <label class="login_lbl" for="zipcode">Zipcode:</label>
          <input id="zipcode" name="zipcode" type="text" />
        </div>
        
        
        <div class="spacer"></div>
        <div class="formRow">
          <input id="create" class="inputButton" type="submit" value="Create"/>
        </div>
       </div>
      </form>
      
      </div>
      <div id="footer">
        Hurry, FaceSpace is waiting for you...
      </div>
    </div>

  </div>
</body>
</html>