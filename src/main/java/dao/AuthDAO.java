package dao;


import java.io.UnsupportedEncodingException;
import net.response.SignOutResponse;


public class AuthDAO {

    private MockDatabase mockDatabase;

    public AuthDAO(){
        mockDatabase = MockDatabase.getInstance();
    }

    public SignOutResponse signOut() {
        return mockDatabase.signOutUser();
    }

    public String generateToken() throws UnsupportedEncodingException{
//        long nowMillis = System.currentTimeMillis();
//        long ttlMillis = System.currentTimeMillis() + 300000;
//        Date now = new Date(nowMillis);
//        Date expiration = new Date(nowMillis+ttlMillis);
//
//        String jwt = Jwts.builder()
//                .setExpiration(new Date(1300819380))
//                .claim("authorization", "valid")
//                .signWith(
//                        SignatureAlgorithm.HS256,
//                        "secret".getBytes("UTF-8")
//                )
//                .compact();

        return "TestAuthorization";
    }

    public boolean validateToken(String token) {
        return token.equals("TestAuthorization");
//        Jws<Claims> claims = Jwts.parser()
//                .setSigningKey("secret".getBytes("UTF-8"))
//                .parseClaimsJws(token);
//
//        String authorization = claims.getBody().get("authorization").toString();
//        System.out.println(authorization);
//        return authorization.equals("valid");
    }

//    private Key getSigningKey() {
//        byte[] keyBytes = Decoders.BASE64.decode("test");
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
}
