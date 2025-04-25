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
								     <h2>Bombastic FC</h2>
								   </td>
								 </tr>
                                 <!-- Password Reset Info -->                                 
                                 <tr>
                                    <td style="font-family: 'Open Sans', sans-serif; color: #14074c; text-align:left;" st-title="rightimage-title">
											<p>A password request request has been submitted on your behalf. Please click on the following
											link if you request the reset:
											</p>
                                    </td>
                                 </tr>
                                 <tr>
                                 	<td align="center"  style="font-family: 'Open Sans', sans-serif; color: #14074c; text-align: middle;">
                                 		<a href="${url}">Password Reset Link</a>
                                 	</td>
                                 </tr>
                              </tbody>
                           </table>
                        </td>
                     </tr>
                     <tr>
                        <td width="100%" height="20"></td>
                     </tr>
                  </tbody>
               </table>
            </td>
         </tr>
      </tbody>
   </table>
</div>
<#include "email-footer.ftl">