package com.phonecompare.app.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.phonecompare.app.domain.model.DatabaseStatus
import com.phonecompare.app.ui.components.BrandWordmark
import com.phonecompare.app.ui.components.PlaceholderBadge
import com.phonecompare.app.ui.components.SearchField
import com.phonecompare.app.ui.theme.ElectricBlue
import com.phonecompare.app.ui.theme.PhoneCompareTheme
import com.phonecompare.app.ui.theme.Spacing

/** Stateful entry point: binds the [HomeViewModel] to the stateless [HomeScreen]. */
@Composable
fun HomeRoute(
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        uiState = uiState,
        onQueryChange = viewModel::onQueryChange,
    )
}

/**
 * Stateless landing screen. A pure function of [HomeUiState] so it is trivial
 * to preview and test.
 */
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.lg)
                .widthIn(max = 640.dp),
        ) {
            Spacer(Modifier.height(Spacing.md))
            BrandWordmark()

            Spacer(Modifier.height(Spacing.xl))
            HeroSection()

            Spacer(Modifier.height(Spacing.lg))
            SearchField(
                query = uiState.query,
                onQueryChange = onQueryChange,
            )
            Text(
                text = if (uiState.isCatalogueReady) {
                    "Search ${uiState.deviceCount} devices."
                } else {
                    "Search activates once the device catalogue is prepared."
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = Spacing.xs, start = Spacing.xxs),
            )

            Spacer(Modifier.height(Spacing.xl))
            WorkspacePreviewCard()

            Spacer(Modifier.height(Spacing.md))
            DatabaseStatusCard(status = uiState.databaseStatus)

            Spacer(Modifier.height(Spacing.xl))
        }
    }
}

@Composable
private fun HeroSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Compare phone screens.",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(Spacing.sm))
        Text(
            text = "See how any two phones really measure up. PhoneCompare draws " +
                "screen outlines at true physical size — in millimetres — so you " +
                "compare real dimensions, not marketing diagonals.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

/**
 * A preview of the comparison workspace: two empty device slots drawn as
 * screen outlines. Clearly badged as a preview so it is never mistaken for a
 * real comparison of real devices.
 */
@Composable
private fun WorkspacePreviewCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        shape = MaterialTheme.shapes.large,
    ) {
        Column(modifier = Modifier.padding(Spacing.lg)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Comparison workspace",
                    style = MaterialTheme.typography.titleMedium,
                )
                PlaceholderBadge(text = "Preview")
            }

            Spacer(Modifier.height(Spacing.md))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.md),
            ) {
                DeviceSlot(label = "Device A", modifier = Modifier.weight(1f))
                DeviceSlot(label = "Device B", modifier = Modifier.weight(1f))
            }

            Spacer(Modifier.height(Spacing.md))
            Text(
                text = "Add devices to overlay their screen outlines and compare " +
                    "width, height, diagonal, resolution, density and aspect ratio.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

/** An empty, dashed device slot — a placeholder outline, not a real device. */
@Composable
private fun DeviceSlot(
    label: String,
    modifier: Modifier = Modifier,
) {
    val outlineColor = MaterialTheme.colorScheme.outline
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentAlignment = Alignment.Center,
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val phoneWidth = size.width * 0.52f
                val phoneHeight = size.height * 0.92f
                val left = (size.width - phoneWidth) / 2f
                val top = (size.height - phoneHeight) / 2f
                drawRoundRect(
                    color = outlineColor,
                    topLeft = Offset(left, top),
                    size = Size(phoneWidth, phoneHeight),
                    cornerRadius = CornerRadius(phoneWidth * 0.14f, phoneWidth * 0.14f),
                    style = Stroke(
                        width = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f)),
                    ),
                )
            }
            Text(
                text = "+",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Spacer(Modifier.height(Spacing.xs))
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

/** Honest status of the device catalogue, reflecting the real record count. */
@Composable
private fun DatabaseStatusCard(
    status: DatabaseStatus,
    modifier: Modifier = Modifier,
) {
    val isReady = status is DatabaseStatus.Ready
    val count = (status as? DatabaseStatus.Ready)?.deviceCount ?: 0

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            modifier = Modifier.padding(Spacing.md),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StatusDot(active = isReady)
            Spacer(Modifier.width(Spacing.sm))
            Column {
                Text(
                    text = if (isReady) "Device database ready" else "Device database — preparing",
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(Modifier.height(Spacing.xxs))
                Text(
                    text = if (isReady) {
                        "$count devices available offline."
                    } else {
                        "The offline catalogue of 10,000+ real smartphone models " +
                            "is being prepared. No device data is loaded yet."
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun StatusDot(active: Boolean) {
    val color = if (active) ElectricBlue else MaterialTheme.colorScheme.onSurfaceVariant
    Canvas(modifier = Modifier.size(10.dp)) {
        drawCircle(color = color)
    }
}

/* ---------------------------------------------------------------------- */
/* Previews                                                               */
/* ---------------------------------------------------------------------- */

@Preview(name = "Home — preparing", showBackground = true, backgroundColor = 0xFF0E0F13)
@Composable
private fun HomeScreenPreparingPreview() {
    PhoneCompareTheme {
        HomeScreen(
            uiState = HomeUiState(databaseStatus = DatabaseStatus.Preparing),
            onQueryChange = {},
        )
    }
}

@Preview(name = "Home — ready", showBackground = true, backgroundColor = 0xFF0E0F13)
@Composable
private fun HomeScreenReadyPreview() {
    PhoneCompareTheme {
        HomeScreen(
            uiState = HomeUiState(
                query = "Pixel",
                databaseStatus = DatabaseStatus.Ready(deviceCount = 10234),
            ),
            onQueryChange = {},
        )
    }
}
