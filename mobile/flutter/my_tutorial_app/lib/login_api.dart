import 'package:google_sign_in/google_sign_in.dart';

class LoginAPi {
  static final _googleSignIn = GoogleSignIn(
    clientId: "628285089276-89nu8l6urllh3qmfj15u30cbloi6v25h.apps.googleusercontent.com"
  );
  static Future<GoogleSignInAccount?> login() => _googleSignIn.signIn();
  static Future signOut = _googleSignIn.signOut();
}