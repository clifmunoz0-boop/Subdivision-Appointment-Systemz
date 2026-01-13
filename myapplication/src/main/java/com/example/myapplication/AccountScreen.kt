package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Account(user: User) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightLavender)
            .padding(20.dp)
    ) {
        Text(
            text = "My Account",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = DeepNavy,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE8EBFA)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE53935)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "👤",
                            fontSize = 32.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = user.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepNavy
                        )
                        Text(
                            text = user.role,
                            fontSize = 14.sp,
                            color = MediumGray
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFB5B9D8))
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = DeepNavy,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Login Credentials",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DeepNavy
                    )
                }

                AccountTextField(
                    label = "Username",
                    value = user.username
                )

                Spacer(modifier = Modifier.height(12.dp))

                AccountTextField(
                    label = "Email",
                    value = user.email
                )

                Spacer(modifier = Modifier.height(12.dp))

                AccountPasswordField(
                    label = "Password",
                    value = user.password
                )

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFB5B9D8))
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = DeepNavy,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Personal Information",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DeepNavy
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFD8DBFA).copy(alpha = 0.5f)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        PersonalInfoRow("Name:", user.name)
                        Spacer(modifier = Modifier.height(8.dp))
                        PersonalInfoRow("Contact Num:", user.contactNum)
                        Spacer(modifier = Modifier.height(8.dp))
                        PersonalInfoRow("Address:", user.address)
                    }
                }
            }
        }
    }
}

@Composable
fun AccountTextField(
    label: String,
    value: String
) {
    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            color = DeepNavy.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFD8DBFA).copy(alpha = 0.5f)
        ) {
            Text(
                text = value,
                fontSize = 14.sp,
                color = DeepNavy,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

@Composable
fun AccountPasswordField(
    label: String,
    value: String
) {
    var showPassword by remember { mutableStateOf(false) }

    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            color = DeepNavy.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFD8DBFA).copy(alpha = 0.5f)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (showPassword) value else "••••••••••••••••",
                    fontSize = 14.sp,
                    color = DeepNavy
                )
                IconButton(
                    onClick = { showPassword = !showPassword },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (showPassword) "Hide password" else "Show password",
                        tint = DeepNavy,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PersonalInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = DeepNavy.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = DeepNavy,
            fontWeight = FontWeight.Normal
        )
    }
}
