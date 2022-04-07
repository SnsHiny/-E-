package com.SnHI.server.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 用户登录管理
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginAdmin implements UserDetails {

    private Admin admin;

    private List<String> permissions;

    private ArrayList<GrantedAuthority> authorities;

    public LoginAdmin(Admin admin, List<String> permissions) {
        this.admin = admin;
        this.permissions = permissions;
    }

    /**
     * 获取权限信息
     * @return
     */
    // 使authorities不参与序列化，出于安全考虑它不能参与序列化
    @JSONField(serialize = false)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }
        authorities = new ArrayList<>();
        // 把permissions中字符串类型的权限信息转换成GrantedAuthority对象存入authorities中
        for (String permission: permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return admin.getEnabled();
    }
}
