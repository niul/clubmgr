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
								     <h2>${msg("email.availability.update.title")}</h2>
								   </td>
								 </tr>
                                 <!-- Contact Info -->
                                 <tr>
                                    <td style="font-family: 'Open Sans', sans-serif; color: #14074c; text-align:left;" st-title="rightimage-title">
											<table>
											  <tr><td><strong>${msg("email.availability.update.name")}:</strong></td></tr>
											  <tr><td>${playerFixtureInfo.player.firstName} ${playerFixtureInfo.player.lastName}</td></tr>
                                			  <tr><td width="100%" height="20"></td></tr>
											  <tr><td><strong>${msg("email.availability.update.date")}:</strong></td></tr>
											  <tr><td>${playerFixtureInfo.fixture.date?string["EEE, MMM d"]} @ ${playerFixtureInfo.fixture.time?string["hh:mm a"]}</td></tr>
                                			  <tr><td width="100%" height="20"></td></tr>
											  <tr><td><strong>${msg("email.fixture.home")}:</strong></td></tr>
											  <tr><td>${playerFixtureInfo.fixture.home}</td></tr>
											  <tr><td width="100%" height="20"></td></tr>
											  <tr><td><strong>${msg("email.fixture.away")}:</strong></td></tr>
											  <tr><td>${playerFixtureInfo.fixture.away}</td></tr>
											  <tr><td width="100%" height="20"></td></tr>
											  <tr><td><strong>${msg("email.availability.update.newStatus")}:</strong></td></tr>
											  <tr><td>${playerFixtureInfo.status}</td></tr>
											  <tr><td width="100%" height="20"></td></tr>
											  <tr><td><strong>${msg("email.availability.update.oldStatus")}:</strong></td></tr>
											  <tr><td>${oldStatus}</td></tr>
											</table>
                                    </td>
                                 </tr>
                                 <!-- end of title -->
                                  <!-- Spacing -->
                                 <tr>
                                    <td width="100%" height="20"></td>
                                 </tr>
                                 <!-- Spacing -->
                                 <tr>
                                    <td style="font-family: 'Open Sans', sans-serif; font-size: 13px; color: #14074c; text-align:left;line-height: 24px;" st-content="rightimage-paragraph">
                                       ${msg("email.fixture.status.1")} <a href="${msg("email.fixture.response.url")}?uuid=${playerFixtureInfo.fixture.uuid}&player=${playerFixtureInfo.uuid}" style="font-weight: bold;">${msg("email.fixture.status.2")}</a> ${msg("email.fixture.status.3")}
                                    </td>
                                 </tr>
                                 <!-- end of content -->
                                 <!-- Spacing -->
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