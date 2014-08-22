package org.cobbzilla.sendgrid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.cobbzilla.util.reflect.ReflectionUtil;
import org.cobbzilla.util.string.StringUtil;

@Accessors(chain=true) @NoArgsConstructor
public class SendGridUser {

    public SendGridUser(SendGridJsonUser user) {
        ReflectionUtil.copy(this, user.getCredential());
    }

    @Getter @Setter private long id;

    @Getter @Setter private String created_at;
    @Getter @Setter private String updated_at;

    @JsonIgnore @Getter @Setter private String username;

    @Getter @Setter private String password;
    public boolean hasPassword() { return !StringUtil.empty(password); }

    // it's called "name" in the JSON responses, but username in requests
    public String getName () { return getUsername(); }
    public SendGridUser setName (String name) { setUsername(name); return this; }

    @Getter @Setter private SendGridPermissions permissions;
}
