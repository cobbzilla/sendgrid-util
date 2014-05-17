package org.cobbzilla.sendgrid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
public class SendGridCredentials {

    @Getter @Setter private String apiUser;
    @Getter @Setter private String apiKey;

}
