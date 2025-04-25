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
								     <h2>${msg("contact.title")}</h2>
								   </td>
								 </tr>
                                 <!-- Contact Info -->                                 
                                 <tr>
                                    <td style="font-family: 'Open Sans', sans-serif; color: #14074c; text-align:left;" st-title="rightimage-title">
											<table>
											  <tr><td><strong>${msg("contact.name")}:</strong></td></tr>
											  <tr><td>${name}</td></tr>
                                			  <tr><td width="100%" height="20"></td></tr>
											  <tr><td><strong>${msg("contact.email")}:</strong></td></tr>
											  <tr><td>${email}</td></tr>
                                			  <tr><td width="100%" height="20"></td></tr>
											  <tr><td><strong>${msg("contact.message")}:</strong></td></tr>
											  <tr><td>${message}</td></tr>
											</table>
                                    </td>
                                 </tr>
                                 <!-- end of title -->
                                 <!-- Spacing -->
                                 <tr>
                                    <td width="100%" height="20"></td>
                                 </tr>
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