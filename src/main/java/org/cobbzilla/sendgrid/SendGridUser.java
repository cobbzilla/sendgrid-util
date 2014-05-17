package org.cobbzilla.sendgrid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.cobbzilla.util.reflect.ReflectionUtil;
import org.cobbzilla.util.string.StringUtil;

@NoArgsConstructor
public class SendGridUser {

    @Getter @Setter private long id;

    @Getter @Setter private String created_at;
    @Getter @Setter private String updated_at;

    @JsonIgnore @Getter @Setter private String username;

    public SendGridUser(SendGridJsonUser user) {
        ReflectionUtil.copy(this, user.getCredential());
    }

    public SendGridUser withName(String name) { this.username = name; return this; }

    // it's called "name" in the JSON responses, but username in requests
    public String getName () { return getUsername(); }
    public void setName (String name) { setUsername(name); }

    @Getter @Setter private String password;
    public SendGridUser withPassword(String password) { this.password = password; return this; }

    @Getter @Setter private SendGridPermissions permissions;
    public SendGridUser withPermissions(SendGridPermissions permissions) { this.permissions = permissions; return this; }

    public boolean hasPassword() { return !StringUtil.empty(password); }

}
