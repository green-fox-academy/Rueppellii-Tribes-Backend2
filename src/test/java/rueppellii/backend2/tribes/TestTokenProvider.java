//package rueppellii.backend2.tribes;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import rueppellii.backend2.tribes.security.services.UserPrinciple;
//import rueppellii.backend2.tribes.user.ApplicationUser;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static rueppellii.backend2.tribes.security.SecurityConstants.EXPIRATION_TIME;
//import static rueppellii.backend2.tribes.security.SecurityConstants.SECRET;
//
//public class TestTokenProvider {
//
//    private String jwtSecret = SECRET;
//    private Long jwtExpiration = EXPIRATION_TIME;
//
//    public static UserPrinciple build(ApplicationUser applicationUser) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(applicationUser.getRole()));
//
//        return new UserPrinciple(
//                applicationUser.getId(),
//                applicationUser.getUsername(),
//                applicationUser.getPassword(),
//                authorities);
//    }
//
//    public String generateJwtToken(ApplicationUser applicationUser) {
//
//        UserPrinciple userPrinciple = build(applicationUser);
//
//        return Jwts.builder()
//                .setSubject((userPrinciple.getUsername()))
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//    }
//
//    public String getUserNameFromJwtToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(jwtSecret)
//                .parseClaimsJws(token)
//                .getBody().getSubject();
//    }
//}
