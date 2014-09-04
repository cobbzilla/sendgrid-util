package org.cobbzilla.sendgrid;

import lombok.NoArgsConstructor;
import org.apache.http.entity.ContentType;
import org.cobbzilla.util.string.StringUtil;
import org.cobbzilla.wizard.client.ApiClientBase;
import org.cobbzilla.util.http.ApiConnectionInfo;
import org.cobbzilla.wizard.util.RestResponse;

import java.util.Map;

@NoArgsConstructor
public class SendGridApiClient extends ApiClientBase {

    public SendGridApiClient (String baseUri) {
        super(new ApiConnectionInfo(baseUri));
    }

    public RestResponse post(String path, Map<String, String> params) throws Exception {
        return super.post(path, StringUtil.urlParameterize(params), ContentType.APPLICATION_FORM_URLENCODED);
    }

}
