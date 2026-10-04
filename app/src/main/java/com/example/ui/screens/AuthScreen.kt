package com.example.ui.screens

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.auth.AuthState
import com.example.ui.MainViewModel
import com.example.ui.theme.GlassBorderTop
import com.example.ui.theme.IosGray1
import com.example.ui.theme.PureBlack
import com.example.ui.theme.PureWhite
import com.example.ui.theme.White04
import com.example.ui.theme.White08
import com.example.ui.theme.White12
import com.example.ui.theme.White18
import com.example.ui.theme.White30
import com.example.ui.theme.White75
import com.example.ui.theme.liquidBackgroundEffect
import com.example.ui.theme.liquidGlass

@Composable
fun AuthScreen(
  viewModel: MainViewModel,
  authState: AuthState,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val focusManager = LocalFocusManager.current

  var isSignUp by remember { mutableStateOf(false) }
  var name by remember { mutableStateOf("") }
  var email by remember { mutableStateOf("") }
  var password by remember { mutableStateOf("") }
  var isPasswordVisible by remember { mutableStateOf(false) }

  Box(
    modifier = modifier
      .fillMaxSize()
      .liquidBackgroundEffect()
      .statusBarsPadding()
      .navigationBarsPadding()
      .imePadding()
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 24.dp, vertical = 20.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      // Monochrome Minimal Cloud Icon
      Box(
        modifier = Modifier
          .size(72.dp)
          .clip(CircleShape)
          .background(White12)
          .border(1.dp, White30, CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Cloud,
          contentDescription = "1x Storage",
          tint = PureWhite,
          modifier = Modifier.size(38.dp)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "1X STORAGE",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        letterSpacing = 0.5.sp,
        color = PureWhite
      )

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = 4.dp)
      ) {
        Text(
          text = "MADE BY ",
          fontSize = 12.sp,
          fontWeight = FontWeight.Normal,
          color = IosGray1,
          letterSpacing = 1.5.sp
        )
        Text(
          text = "FINEX",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          fontStyle = FontStyle.Italic,
          color = PureWhite,
          letterSpacing = 1.5.sp
        )
      }

      // 50 GB Free Minimal Pill
      Box(
        modifier = Modifier
          .padding(top = 10.dp)
          .clip(RoundedCornerShape(50.dp))
          .background(White08)
          .border(1.dp, White18, RoundedCornerShape(50.dp))
          .padding(horizontal = 14.dp, vertical = 5.dp)
      ) {
        Text(
          text = "FREE 50 GB CLOUD STORAGE",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          fontStyle = FontStyle.Italic,
          color = PureWhite,
          letterSpacing = 0.5.sp
        )
      }

      Spacer(modifier = Modifier.height(28.dp))

      // Main Glass Card for Auth
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .liquidGlass(
            shape = RoundedCornerShape(26.dp),
            backgroundColor = Color(0x18FFFFFF),
            borderColor = GlassBorderTop
          )
          .padding(22.dp)
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          // Tab Switcher: Login / Create Account (iOS Segmented Control)
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(14.dp))
              .background(White08)
              .border(1.dp, White18, RoundedCornerShape(14.dp))
              .padding(3.dp)
          ) {
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(11.dp))
                .background(if (!isSignUp) PureWhite else Color.Transparent)
                .clickable { isSignUp = false }
                .padding(vertical = 9.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "Log In",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 13.sp,
                color = if (!isSignUp) PureBlack else White75
              )
            }

            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(11.dp))
                .background(if (isSignUp) PureWhite else Color.Transparent)
                .clickable { isSignUp = true }
                .padding(vertical = 9.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "Create Account",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 13.sp,
                color = if (isSignUp) PureBlack else White75
              )
            }
          }

          Spacer(modifier = Modifier.height(20.dp))

          // Error Message if present
          if (authState is AuthState.Error) {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(White12)
                .border(1.dp, White30, RoundedCornerShape(12.dp))
                .padding(12.dp)
            ) {
              Text(
                text = authState.message,
                color = PureWhite,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
              )
            }
          }

          // Full Name field for sign up
          AnimatedVisibility(visible = isSignUp) {
            Column {
              OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = {
                  Text(
                    text = "Full Name",
                    fontWeight = FontWeight.Medium
                  )
                },
                leadingIcon = {
                  Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = PureWhite
                  )
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = PureWhite,
                  unfocusedBorderColor = White18,
                  focusedContainerColor = White08,
                  unfocusedContainerColor = White04,
                  focusedTextColor = PureWhite,
                  unfocusedTextColor = PureWhite,
                  focusedLabelColor = PureWhite,
                  unfocusedLabelColor = IosGray1
                ),
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("name_input")
              )
              Spacer(modifier = Modifier.height(14.dp))
            }
          }

          // Email Field
          OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
              Text(
                text = "Email Address",
                fontWeight = FontWeight.Medium
              )
            },
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                tint = PureWhite
              )
            },
            keyboardOptions = KeyboardOptions(
              keyboardType = KeyboardType.Email,
              imeAction = ImeAction.Next
            ),
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = PureWhite,
              unfocusedBorderColor = White18,
              focusedContainerColor = White08,
              unfocusedContainerColor = White04,
              focusedTextColor = PureWhite,
              unfocusedTextColor = PureWhite,
              focusedLabelColor = PureWhite,
              unfocusedLabelColor = IosGray1
            ),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("email_input")
          )

          Spacer(modifier = Modifier.height(14.dp))

          // Password Field
          OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
              Text(
                text = "Password",
                fontWeight = FontWeight.Medium
              )
            },
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = PureWhite
              )
            },
            trailingIcon = {
              IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(
                  imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                  contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                  tint = IosGray1
                )
              }
            },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
              keyboardType = KeyboardType.Password,
              imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
              focusManager.clearFocus()
              if (isSignUp) {
                viewModel.signUpWithEmail(email, password, name)
              } else {
                viewModel.signInWithEmail(email, password)
              }
            }),
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = PureWhite,
              unfocusedBorderColor = White18,
              focusedContainerColor = White08,
              unfocusedContainerColor = White04,
              focusedTextColor = PureWhite,
              unfocusedTextColor = PureWhite,
              focusedLabelColor = PureWhite,
              unfocusedLabelColor = IosGray1
            ),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("password_input")
          )

          Spacer(modifier = Modifier.height(20.dp))

          // Primary Submit Button (Solid Pure White)
          val isLoading = authState is AuthState.Loading
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp)
              .clip(RoundedCornerShape(14.dp))
              .background(PureWhite)
              .clickable(enabled = !isLoading) {
                focusManager.clearFocus()
                if (isSignUp) {
                  viewModel.signUpWithEmail(email, password, name)
                } else {
                  viewModel.signInWithEmail(email, password)
                }
              }
              .testTag("submit_auth_button"),
            contentAlignment = Alignment.Center
          ) {
            if (isLoading) {
              CircularProgressIndicator(
                color = PureBlack,
                strokeWidth = 2.dp,
                modifier = Modifier.size(20.dp)
              )
            } else {
              Text(
                text = if (isSignUp) "CREATE 1X ACCOUNT" else "LOG IN TO 1X STORAGE",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 14.sp,
                letterSpacing = 0.5.sp,
                color = PureBlack
              )
            }
          }

          Spacer(modifier = Modifier.height(18.dp))

          // Divider
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            HorizontalDivider(
              modifier = Modifier.weight(1f),
              color = White08
            )
            Text(
              text = "  OR  ",
              fontSize = 11.sp,
              fontWeight = FontWeight.Normal,
              color = IosGray1
            )
            HorizontalDivider(
              modifier = Modifier.weight(1f),
              color = White08
            )
          }

          Spacer(modifier = Modifier.height(18.dp))

          // Continue with Google Button (Frosted Liquid Glass)
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .clip(RoundedCornerShape(14.dp))
              .background(White12)
              .border(1.dp, White30, RoundedCornerShape(14.dp))
              .clickable(enabled = !isLoading) {
                viewModel.signInWithGoogle(context)
              }
              .testTag("google_signin_button"),
            contentAlignment = Alignment.Center
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Box(
                modifier = Modifier
                  .size(24.dp)
                  .clip(CircleShape)
                  .background(PureWhite),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = "G",
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp,
                  color = PureBlack
                )
              }

              Spacer(modifier = Modifier.width(10.dp))

              Text(
                text = "Continue with Google",
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Italic,
                fontSize = 14.sp,
                color = PureWhite
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Demo finex access button
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(42.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(White04)
              .border(1.dp, White18, RoundedCornerShape(12.dp))
              .clickable {
                viewModel.continueAsDemo()
              }
              .testTag("demo_access_button"),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "Instant Access as finex (50 GB Cloud)",
              fontWeight = FontWeight.Medium,
              fontStyle = FontStyle.Italic,
              fontSize = 12.sp,
              color = White75
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      Text(
        text = "Free 50 GB Cloud Storage by finex.\nEncrypted & Synced to 1x Cloud.",
        fontSize = 11.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
        color = IosGray1,
        lineHeight = 16.sp
      )
    }
  }
}
