package org.cobbzilla.sendgrid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import static org.cobbzilla.util.daemon.ZillaRuntime.empty;
import static org.cobbzilla.util.reflect.ReflectionUtil.copy;

@Accessors(chain=true) @NoArgsConstructor
@EqualsAndHashCode(of="username")
@JsonIgnoreProperties(ignoreUnknown=true)
public class SendGridUser {

    public SendGridUser(SendGridJsonUser user) { copy(this, user.getCredential()); }

    @Getter @Setter private long id;

    @Getter @Setter private String created_at;
    @Getter @Setter private String updated_at;
    @Getter @Setter private String pw_updated_at;

    @JsonIgnore @Getter @Setter private String username;

    @Getter @Setter private String password;
    public boolean hasPassword() { return !empty(password); }

    // it's called "name" in the JSON responses, but username in requests
    public String getName () { return getUsername(); }
    public SendGridUser setName (String name) { setUsername(name); return this; }

    @Getter @Setter private SendGridPermissions permissions;
}
