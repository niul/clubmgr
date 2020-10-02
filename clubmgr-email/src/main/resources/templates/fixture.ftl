<#include "email-header.ftl">
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
								   <td align="center" style="font-family: 'Open Sans', sans-serif; color: #14074c; text-align: middle; ">
								     <h2>${msg("email.fixture.team")} ${fixture.team.name}</h2>
								   </td>
								 </tr>
                                 <!-- Fixture Info -->                                 
                                 <tr>
                                    <td style="font-family: 'Open Sans', sans-serif; color: #14074c; text-align:left;" st-title="rightimage-title">
											<h4>${playerFixtureInfo.player.firstName} - you have an upcoming fixture</h4>
											<table>
											  <tr><td align="right"><strong>${msg("email.fixture.home")}:</strong></td><td>${fixture.home}</td></tr>
											  <tr><td align="right"><strong>${msg("email.fixture.away")}:</strong></td><td>${fixture.away}</td></tr>
											  <tr><td align="right"><strong>${msg("email.fixture.date")}:</strong></td><td>${fixture.date?string["EEE, MMM d"]} @ ${fixture.time?string["hh:mm a"]}</td></tr>
											  <tr><td align="right"><strong>${msg("email.fixture.field")}:</strong></td><td><a href="${fixture.fieldMapUri}">${fixture.field}</a></td></tr>
											</table>
                                    </td>
                                 </tr>
                                 <!-- end of title -->
                                 <!-- Spacing -->
                                 <tr>
                                    <td width="100%" height="20"></td>
                                 </tr>
                                 <!-- Spacing -->
                                 <!-- content -->
                                 <tr>
                                    <td style="font-family: 'Open Sans', sans-serif; font-size: 13px; color: #14074c; text-align:left;line-height: 24px;" st-content="rightimage-paragraph">
                                       ${msg("email.fixture.response.text")}:
                                    </td>
                                 </tr>
                                 <!-- end of content -->
                                 <!-- button -->
                                 <tr>
                                    <td>
                                       <table width="100%" height="30" align="left" valign="middle" border="0" cellpadding="0" cellspacing="0" class="tablet-button" style="border-collapse: separate; border-spacing: 20px;" st-button="edit">
                                         <tbody>
                                           <tr width="100%" align="center">
                                             <td width="40px" align="center" valign="middle" height="30" style=" background-color: #14892c; border-top-left-radius:4px; border-bottom-left-radius:4px;border-top-right-radius:4px; border-bottom-right-radius:4px; background-clip: padding-box;font-size:13px; font-family:'Open Sans', sans-serif; text-align:center;  color:#ffffff; font-weight: 300;">
                                               <span style="color: #ffffff; font-weight: bold;">
                                                 <a style="color: #ffffff; text-align:center; text-decoration: none;" href="${msg("email.fixture.response.url")}?uuid=${fixture.uuid}&player=${playerFixtureInfo.uuid}&status=YES">
                                                   ${msg("email.fixture.response.yes")}
                                                 </a>
                                               </span>
                                             </td>
                                             <td width="40px" align="center" valign="middle" height="30" style=" background-color: #ffd351; border-top-left-radius:4px; border-bottom-left-radius:4px;border-top-right-radius:4px; border-bottom-right-radius:4px; background-clip: padding-box;font-size:13px; font-family:'Open Sans', sans-serif; text-align:center;  color:#ffffff; font-weight: 300;">
                                               <span style="color: #594300; font-weight: bold;">
                                                 <a style="color: #594300; text-align:center; text-decoration: none;" href="${msg("email.fixture.response.url")}?uuid=${fixture.uuid}&player=${playerFixtureInfo.uuid}&status=MAYBE">
                                                   ${msg("email.fixture.response.maybe")}
                                                 </a>
                                               </span>
                                             </td>
                                             <td width="40px" align="center" valign="middle" height="30" style=" background-color: #d04437; border-top-left-radius:4px; border-bottom-left-radius:4px;border-top-right-radius:4px; border-bottom-right-radius:4px; background-clip: padding-box;font-size:13px; font-family:'Open Sans', sans-serif; text-align:center;  color:#ffffff; font-weight: 300;">
                                               <span style="color: #ffffff; font-weight: bold;">
                                                 <a style="color: #ffffff; text-align:center; text-decoration: none;" href="${msg("email.fixture.response.url")}?uuid=${fixture.uuid}&player=${playerFixtureInfo.uuid}&status=NO">
                                                   ${msg("email.fixture.response.no")}
                                                 </a>
                                               </span>
                                             </td>
                                           </tr>
                                         </tbody>
                                       </table>
                                    </td>
                                 </tr>
                                 <!-- /button -->
                                 <!-- Spacing -->
                                 <tr>
                                    <td width="100%" height="20"></td>
                                 </tr>
                                 <!-- Spacing -->
                                 <tr>
                                    <td style="font-family: 'Open Sans', sans-serif; font-size: 13px; color: #14074c; text-align:left;line-height: 24px;" st-content="rightimage-paragraph">
                                       ${msg("email.fixture.status.1")} <a href="${msg("email.fixture.response.url")}?uuid=${fixture.uuid}&player=${playerFixtureInfo.uuid}" style="font-weight: bold;">${msg("email.fixture.status.2")}</a> ${msg("email.fixture.status.3")}
                                    </td>
                                 </tr>
                                 <!-- end of content -->
                                 <!-- Spacing -->
                                 <tr>
                                    <td width="100%" height="10"></td>
                                    <img src="${msg("email.fixture.tracker.url")}?player=${playerFixtureInfo.uuid}">
                                 </tr>
                              </tbody>
                           </table>
                        </td>
                     </tr>
                  </tbody>
               </table>
            </td>
         </tr>
      </tbody>
   </table>
</div>
<#include "email-footer.ftl">