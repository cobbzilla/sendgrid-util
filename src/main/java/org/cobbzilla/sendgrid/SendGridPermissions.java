package org.cobbzilla.sendgrid;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.cobbzilla.util.json.JsonUtil;
import org.cobbzilla.util.reflect.ReflectionUtil;

@NoArgsConstructor
public class SendGridPermissions {

    @Getter @Setter private int email = 0;
    public SendGridPermissions withEmail() { email = 1; return this; }

    @Getter @Setter private int web = 0;
    public SendGridPermissions withWeb() { web = 1; return this; }

    @Getter @Setter private int api = 0;
    public SendGridPermissions withApi() { api = 1; return this; }

    public SendGridPermissions withAll() { email = web = api = 1; return this; }

    public SendGridPermissions (String json) {
        try {
            ReflectionUtil.copy(this, JsonUtil.fromJson(json, getClass()));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid json: "+json+": "+e, e);
        }
    }

    @Override
    public String toString() {
        return new StringBuilder().append("{")
                .append("\"email\": ").append(email)
                .append(", \"web\": ").append(web)
                .append(", \"api\": ").append(api)
                .append("}").toString();
    }

}
