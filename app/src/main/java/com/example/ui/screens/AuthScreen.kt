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
import androidx.compose.ui.graphics.Brush
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
import com.example.ui.theme.GlassBorderCyan
import com.example.ui.theme.GlassBorderShine
import com.example.ui.theme.GlassBorderSubtle
import com.example.ui.theme.GlassFillDeep
import com.example.ui.theme.LiquidCyan
import com.example.ui.theme.LiquidIceBlue
import com.example.ui.theme.LiquidSpaceDark
import com.example.ui.theme.LiquidViolet
import com.example.ui.theme.TextCyanGlow
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
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
      // Branding Header
      Box(
        modifier = Modifier
          .size(76.dp)
          .clip(CircleShape)
          .background(
            brush = Brush.radialGradient(
              listOf(Color(0x3300F2FE), Color(0x10B154F0))
            )
          )
          .border(
            width = 1.5.dp,
            brush = Brush.linearGradient(listOf(LiquidCyan, LiquidViolet, Color.White)),
            shape = CircleShape
          ),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Cloud,
          contentDescription = "1x Storage Cloud",
          tint = LiquidCyan,
          modifier = Modifier.size(42.dp)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "1X STORAGE",
        fontSize = 32.sp,
        fontWeight = FontWeight.Black,
        fontStyle = FontStyle.Italic,
        letterSpacing = 1.sp,
        color = TextPrimary
      )

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = 4.dp)
      ) {
        Text(
          text = "MADE BY ",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          fontStyle = FontStyle.Italic,
          color = TextSecondary,
          letterSpacing = 2.sp
        )
        Text(
          text = "FINEX",
          fontSize = 13.sp,
          fontWeight = FontWeight.Black,
          fontStyle = FontStyle.Italic,
          color = LiquidCyan,
          letterSpacing = 2.sp
        )
      }

      // 50 GB Free badge
      Box(
        modifier = Modifier
          .padding(top = 10.dp)
          .clip(RoundedCornerShape(50.dp))
          .background(Color(0x2200F2FE))
          .border(1.dp, Color(0x5500F2FE), RoundedCornerShape(50.dp))
          .padding(horizontal = 14.dp, vertical = 5.dp)
      ) {
        Text(
          text = "✦ FREE 50 GB CLOUD STORAGE ✦",
          fontSize = 11.sp,
          fontWeight = FontWeight.ExtraBold,
          fontStyle = FontStyle.Italic,
          color = TextCyanGlow,
          letterSpacing = 1.sp
        )
      }

      Spacer(modifier = Modifier.height(28.dp))

      // Main Glass Card for Auth
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .liquidGlass(
            shape = RoundedCornerShape(28.dp),
            backgroundColor = GlassFillDeep,
            borderColor = GlassBorderShine
          )
          .padding(22.dp)
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          // Tab Switcher: Login / Create Account
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(16.dp))
              .background(Color(0x1AFFFFFF))
              .border(1.dp, GlassBorderSubtle, RoundedCornerShape(16.dp))
              .padding(4.dp)
          ) {
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(
                  if (!isSignUp) Brush.linearGradient(listOf(LiquidCyan.copy(alpha = 0.35f), LiquidIceBlue.copy(alpha = 0.2f)))
                  else Brush.linearGradient(listOf(Color.Transparent, Color.Transparent))
                )
                .clickable { isSignUp = false }
                .padding(vertical = 10.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "Log In",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 14.sp,
                color = if (!isSignUp) TextPrimary else TextSecondary
              )
            }

            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(
                  if (isSignUp) Brush.linearGradient(listOf(LiquidCyan.copy(alpha = 0.35f), LiquidIceBlue.copy(alpha = 0.2f)))
                  else Brush.linearGradient(listOf(Color.Transparent, Color.Transparent))
                )
                .clickable { isSignUp = true }
                .padding(vertical = 10.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "Create Account",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 14.sp,
                color = if (isSignUp) TextPrimary else TextSecondary
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
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0x2BFF5376))
                .border(1.dp, Color(0x66FF5376), RoundedCornerShape(14.dp))
                .padding(12.dp)
            ) {
              Text(
                text = authState.message,
                color = Color(0xFFFF9EB2),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
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
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                  )
                },
                leadingIcon = {
                  Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = LiquidCyan
                  )
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = LiquidCyan,
                  unfocusedBorderColor = GlassBorderSubtle,
                  focusedContainerColor = Color(0x18FFFFFF),
                  unfocusedContainerColor = Color(0x0CFFFFFF),
                  focusedTextColor = TextPrimary,
                  unfocusedTextColor = TextPrimary,
                  focusedLabelColor = LiquidCyan,
                  unfocusedLabelColor = TextSecondary
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
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
              )
            },
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                tint = LiquidCyan
              )
            },
            keyboardOptions = KeyboardOptions(
              keyboardType = KeyboardType.Email,
              imeAction = ImeAction.Next
            ),
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = LiquidCyan,
              unfocusedBorderColor = GlassBorderSubtle,
              focusedContainerColor = Color(0x18FFFFFF),
              unfocusedContainerColor = Color(0x0CFFFFFF),
              focusedTextColor = TextPrimary,
              unfocusedTextColor = TextPrimary,
              focusedLabelColor = LiquidCyan,
              unfocusedLabelColor = TextSecondary
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
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
              )
            },
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = LiquidCyan
              )
            },
            trailingIcon = {
              IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(
                  imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                  contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                  tint = TextSecondary
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
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = LiquidCyan,
              unfocusedBorderColor = GlassBorderSubtle,
              focusedContainerColor = Color(0x18FFFFFF),
              unfocusedContainerColor = Color(0x0CFFFFFF),
              focusedTextColor = TextPrimary,
              unfocusedTextColor = TextPrimary,
              focusedLabelColor = LiquidCyan,
              unfocusedLabelColor = TextSecondary
            ),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("password_input")
          )

          Spacer(modifier = Modifier.height(20.dp))

          // Primary Submit Button
          val isLoading = authState is AuthState.Loading
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(52.dp)
              .clip(RoundedCornerShape(16.dp))
              .background(
                brush = Brush.horizontalGradient(
                  colors = listOf(LiquidCyan, LiquidIceBlue, LiquidViolet)
                )
              )
              .border(1.dp, Color(0x80FFFFFF), RoundedCornerShape(16.dp))
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
                color = LiquidSpaceDark,
                strokeWidth = 2.5.dp,
                modifier = Modifier.size(24.dp)
              )
            } else {
              Text(
                text = if (isSignUp) "CREATE 1X ACCOUNT" else "LOG IN TO 1X STORAGE",
                fontWeight = FontWeight.Black,
                fontStyle = FontStyle.Italic,
                fontSize = 15.sp,
                letterSpacing = 0.5.sp,
                color = LiquidSpaceDark
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
              color = Color(0x26FFFFFF)
            )
            Text(
              text = "  OR  ",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              fontStyle = FontStyle.Italic,
              color = TextMuted
            )
            HorizontalDivider(
              modifier = Modifier.weight(1f),
              color = Color(0x26FFFFFF)
            )
          }

          Spacer(modifier = Modifier.height(18.dp))

          // Continue with Google Button
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp)
              .clip(RoundedCornerShape(16.dp))
              .background(Color(0x1FFFFFFF))
              .border(1.2.dp, GlassBorderShine, RoundedCornerShape(16.dp))
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
              // Custom Google "G" Badge
              Box(
                modifier = Modifier
                  .size(26.dp)
                  .clip(CircleShape)
                  .background(Color.White),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = "G",
                  fontWeight = FontWeight.Black,
                  fontSize = 16.sp,
                  color = Color(0xFF4285F4)
                )
              }

              Spacer(modifier = Modifier.width(12.dp))

              Text(
                text = "Continue with Google",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 14.sp,
                color = TextPrimary
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Demo / Quick Access finex button
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(44.dp)
              .clip(RoundedCornerShape(14.dp))
              .background(Color(0x1000F2FE))
              .border(1.dp, Color(0x3300F2FE), RoundedCornerShape(14.dp))
              .clickable {
                viewModel.continueAsDemo()
              }
              .testTag("demo_access_button"),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "⚡ Instant Access as finex (50 GB Cloud)",
              fontWeight = FontWeight.Bold,
              fontStyle = FontStyle.Italic,
              fontSize = 12.sp,
              color = LiquidCyan
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      Text(
        text = "Free 50 GB Cloud Storage provided by finex.\nEncrypted & Synced to Cloud.",
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        fontStyle = FontStyle.Italic,
        textAlign = TextAlign.Center,
        color = TextMuted,
        lineHeight = 16.sp
      )
    }
  }
}
