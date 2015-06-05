package org.cobbzilla.sendgrid;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import static org.cobbzilla.util.daemon.ZillaRuntime.die;
import static org.cobbzilla.util.json.JsonUtil.fromJson;
import static org.cobbzilla.util.reflect.ReflectionUtil.copy;

@NoArgsConstructor @Accessors(chain=true)
public class SendGridPermissions {

    @Getter @Setter private int email = 0;
    public SendGridPermissions setEmail() { email = 1; return this; }

    @Getter @Setter private int web = 0;
    public SendGridPermissions setWeb() { web = 1; return this; }

    @Getter @Setter private int api = 0;
    public SendGridPermissions setApi() { api = 1; return this; }

    public SendGridPermissions setAll() { email = web = api = 1; return this; }

    public SendGridPermissions (String json) {
        try {
            copy(this, fromJson(json, getClass()));
        } catch (Exception e) {
            // print out the JSON, if the formats changes this lets us see what's unexpected
            die("Invalid json: " + json + ": " + e, e);
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
