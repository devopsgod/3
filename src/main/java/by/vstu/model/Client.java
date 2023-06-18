package by.vstu.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "oauth_client")
public class Client implements ClientDetails {

    @Id
    @Column(name = "oc_client_id", nullable = false, length = 50)
    private String clientId;

    @Column(name = "oc_client_secret", nullable = false, length = 60)
    private String clientSecret;

    @Column(name = "oc_resource_ids", nullable = false)
    private String resourceIds;

    @Column(name = "oc_scope", nullable = false)
    private String scope;

    @Column(name = "oc_grant_types", nullable = false)
    private String grantTypes;

    @Column(name = "oc_authorities")
    private String authorities;

    @Column(name = "oc_access_token_expiration", nullable = false)
    private Integer accessTokenExpiration;

    @Column(name = "oc_refresh_token_expiration")
    private Integer refreshTokenExpiration;

    @Column(name = "oc_additional_information")
    private String additionalInformation;

    @Column(name = "oc_auto_approve")
    private String autoApprove;

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public boolean isScoped() {
        return !getScope().isEmpty();
    }

    @Override
    public Set<String> getScope() {
        Set<String> result = new HashSet<>();
        if (StringUtils.hasText(scope)) {
            result = Arrays.stream(scope.split(",")).collect(Collectors.toSet());
        }
        return result;
    }

    @Override
    public Set<String> getResourceIds() {
        Set<String> result = new HashSet<>();
        if (StringUtils.hasText(resourceIds)) {
            result = Arrays.stream(resourceIds.split(",")).collect(Collectors.toSet());
        }
        return result;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        Set<String> result = new HashSet<>();
        if (StringUtils.hasText(grantTypes)) {
            result = Arrays.stream(grantTypes.split(",")).collect(Collectors.toSet());
        }
        return result;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return new HashSet<>();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> result = new HashSet<>();
        if (StringUtils.hasText(authorities)) {
            result = Arrays.stream(authorities.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        }
        return result;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenExpiration;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenExpiration;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        boolean autoApproveScope = false;
        if (StringUtils.hasText(autoApprove)) {
            autoApproveScope = Arrays.stream(autoApprove.split(",")).anyMatch(scope::matches);
        }
        return autoApproveScope;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return new HashMap<>();
    }
}
