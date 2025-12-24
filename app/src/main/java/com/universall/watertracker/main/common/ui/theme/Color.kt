package com.universall.watertracker.main.common.ui.theme

import androidx.compose.ui.graphics.Color

// Primary Brand Colors
val BrandBlue = Color(0xFF3783EF)
val BrandBlueLight = Color(0xFF6BA3EA) // Very light blue for backgrounds
val BrandBlueDark = Color(0xFF3867DB)

// Functional Colors
val SuccessGreen = Color(0xFF4CAF50)
val WarningOrange = Color(0xFFFF9800)
val ErrorRed = Color(0xFFEF5350)

// Neutrals (Grays for Text and Borders)

val Neutral900 = Color(0xFF121212) // Almost Black (Title Text)
val Neutral850 = Color(0xFF1E1E1E)
val Neutral800 = Color(0xFF2A2A2A)
val Neutral750 = Color(0xFF363636)
val Neutral700 = Color(0xFF424242) // Dark Gray (Body Text)
val Neutral650 = Color(0xFF5A5A5A)
val Neutral600 = Color(0xFF757575)
val Neutral550 = Color(0xFF8A8A8A)
val Neutral500 = Color(0xFF9E9E9E) // Medium Gray (Placeholders/Icons)
val Neutral450 = Color(0xFFB0B0B0)
val Neutral400 = Color(0xFFBDBDBD)
val Neutral350 = Color(0xFFD0D0D0)
val Neutral300 = Color(0xFFE0E0E0)
val Neutral250 = Color(0xFFE8E8E8)
val Neutral200 = Color(0xFFEEEEEE) // Light Gray (Dividers/Borders)
val Neutral150 = Color(0xFFF2F2F2)
val Neutral100 = Color(0xFFF5F5F5)
val Neutral50  = Color(0xFFF8F9FA) // Off-White (Screen Background)
val White      = Color(0xFFFFFFFF)

// ================= SCHEME (Semantic Mapping) =================

// Primary: Main buttons, active states
val PrimaryColor = BrandBlue
val OnPrimaryColor = White // Text on top of primary

// Secondary: Floating action buttons, highlights
val SecondaryColor = Neutral500
val OnSecondaryColor = White

// Tertiary
val TertiaryColor = Neutral300
val OnTertiaryColor = White

// Backgrounds
val BackgroundColor = Neutral100  // Main screen bg
val SurfaceColor = White  // Cards, Bottom Sheets
val surfaceVariantColor = Neutral150
val PrimaryContainerColor = White

// Text Colors ("On" colors)
val OnBackgroundColor = Neutral900
val OnSurfaceColor = Neutral900
val OnSurfaceVariantColor = Neutral200

// Status
val SuccessColor = SuccessGreen
val ErrorColor = ErrorRed
