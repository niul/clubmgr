<html xmlns="http://www.w3.org/1999/xhtml"><head>
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <title>${msg("email.header.title")}</title>

<#include "email-style.ftl">
      
   </head>
<body>
<div class="block">
   <!-- Start of preheader -->
   <table width="100%" bgcolor="#f7f7f7" cellpadding="0" cellspacing="0" border="0" id="backgroundTable" st-sortable="preheader">
      <tbody>
         <tr>
            <td width="100%">
               <table width="580" cellpadding="0" cellspacing="0" border="0" align="center" class="devicewidth">
                  <tbody>
                     <!-- Spacing -->
                     <tr>
                        <td width="100%" height="5"></td>
                     </tr>
                     <!-- Spacing -->
                     <tr>
                        <!--td align="right" valign="middle" style="font-family: 'Open Sans', sans-serif; font-size: 10px;color: #999999" st-content="preheader">
                           If you cannot read this email, please  <a class="hlite" href="#" style="text-decoration: none; color: #0090c5">click here</a> 
                        </td-->
                     </tr>
                     <!-- Spacing -->
                     <tr>
                        <td width="100%" height="5"></td>
                     </tr>
                     <!-- Spacing -->
                  </tbody>
               </table>
            </td>
         </tr>
      </tbody>
   </table>
   <!-- End of preheader -->
</div>
<div class="block">
   <!-- start of header -->
   <table width="100%" bgcolor="#f7f7f7" cellpadding="0" cellspacing="0" border="0" id="backgroundTable" st-sortable="header">
      <tbody>
         <tr>
            <td>
               <table width="580" bgcolor="#14074c" cellpadding="0" cellspacing="0" border="0" align="center" class="devicewidth" hlitebg="edit" shadow="edit">
                  <tbody>
                     <tr>
                        <td>
                           <!-- logo -->
                           <table width="280" cellpadding="0" cellspacing="0" border="0" align="left" class="devicewidth">
                              <tbody>
                                 <tr>
                                    <td valign="middle" style="font-family: 'Open Sans', sans-serif;font-weight: 800; font-size: 18px; color: #ffffff;line-height: 24px; padding: 10px 10px;" align="left">
                                       ${msg("email.header.title")}
                                    </td>
                                 </tr>
                              </tbody>
                           </table>
                           <!-- End of logo -->
                           <!-- menu -->
                           <table width="280" cellpadding="0" cellspacing="0" border="0" align="right" class="devicewidth">
                              <tbody>
                                 <tr>
                                    <td width="270" valign="middle" style="font-family: 'Open Sans', sans-serif;font-size: 14px; color: #ffffff;line-height: 24px; padding: 10px 0;" align="right" class="menu" st-content="menu">
                                       <a href="${msg("email.header.home.url")}" style="text-decoration: none; color: #ffffff;">${msg("email.header.home")}</a>
                                       &nbsp;|&nbsp;
                                       <a href="${msg("email.header.about.url")}" style="text-decoration: none; color: #ffffff;">${msg("email.header.about")}</a>
                                    </td>
                                    <td width="20"></td>
                                 </tr>
                              </tbody>
                           </table>
                           <!-- End of Menu -->
                        </td>
                     </tr>
                  </tbody>
               </table>
            </td>
         </tr>
      </tbody>
   </table>
   <!-- end of header -->
</div>
<div class="block">
   <!-- image + text -->
   <table width="100%" bgcolor="#f7f7f7" cellpadding="0" cellspacing="0" border="0" id="backgroundTable" st-sortable="bigimage">
      <tbody>
         <tr>
            <td>
               <table bgcolor="#ffffff" width="580" align="center" cellspacing="0" cellpadding="0" border="0" class="devicewidth" modulebg="edit">
                  <tbody>
                     <tr>
                        <td width="100%" height="20"></td>
                     </tr>
                     <tr>
                        <td>
                           <table width="540" align="center" cellspacing="0" cellpadding="0" border="0" class="devicewidthinner">
                              <tbody>
                                 <tr>
                                    <!-- start of image -->
                                    <td align="center">
                                       <a target="_blank" href="${msg("email.header.home.url")}"><img width="160" border="0" height="160" alt="" style="display:block; border:none; outline:none; text-decoration:none;" src="${msg("email.header.image.url")}" class="bigimage"></a>
                                    </td>
                                 </tr>
                              </tbody>
                           </table>
                        </td>
                     </tr>
                  </tbody>
               </table>
            </td>
         </tr>
      <tbody>
   </table>   