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
val Neutral700 = Color(0xFF424242) // Dark Gray (Body Text)
val Neutral500 = Color(0xFF9E9E9E) // Medium Gray (Placeholders/Icons)
val Neutral200 = Color(0xFFEEEEEE) // Light Gray (Dividers/Borders)
val Neutral50 = Color(0xFFF8F9FA)  // Off-White (Screen Background)
val White = Color(0xFFFFFFFF)

// ================= SCHEME (Semantic Mapping) =================

// Primary: Main buttons, active states
val PrimaryColor = BrandBlue
val OnPrimaryColor = White // Text on top of primary

// Secondary: Floating action buttons, highlights
val SecondaryColor = BrandBlueDark
val OnSecondaryColor = White

// Backgrounds
val BackgroundColor = Neutral50  // Main screen bg
val SurfaceColor = White         // Cards, Bottom Sheets
val PrimaryContainerColor = White

// Text Colors ("On" colors)
val OnBackgroundColor = Neutral900
val OnSurfaceColor = Neutral900
val OnSurfaceVariantColor = Neutral200

// Status
val SuccessColor = SuccessGreen
val ErrorColor = ErrorRed
