package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.text.NumberFormat
import java.util.Locale

// Model Data Transaksi Keuangan
data class Transaction(
    val id: Int,
    val title: String,
    val amount: Double,
    val type: String, // "INCOME" atau "EXPENSE"
    val date: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FinanceAppNavigator()
                }
            }
        }
    }
}

@Composable
fun FinanceAppNavigator() {
    // State untuk Navigasi Layar: "home" atau "add"
    var currentScreen by remember { mutableStateOf("home") }

    // State untuk daftar transaksi
    val transactionList = remember {
        mutableStateListOf(
            Transaction(1, "Gaji", 300000.0, "INCOME", "21 Okt 2026"),
            Transaction(2, "Makanan", 15000.0, "EXPENSE", "21 Okt 2026")
        )
    }

    // Navigasi antar layar
    when (currentScreen) {
        "home" -> MainScreen(
            transactions = transactionList,
            onNavigateToAdd = { currentScreen = "add" },
            onDeleteTransaction = { transactionList.remove(it) }
        )
        "add" -> AddTransactionScreen(
            onBack = { currentScreen = "home" },
            onSave = { newTitle, newAmount, newType ->
                val newId = transactionList.size + 1
                transactionList.add(0, Transaction(newId, newTitle, newAmount, newType, "21 Okt 2026"))
                currentScreen = "home"
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    transactions: List<Transaction>,
    onNavigateToAdd: () -> Unit,
    onDeleteTransaction: (Transaction) -> Unit
) {
    // Hitung total saldo, pemasukan, dan pengeluaran secara interaktif
    val totalIncome = transactions.filter { it.type == "INCOME" }.sumOf { it.amount }
    val totalExpense = transactions.filter { it.type == "EXPENSE" }.sumOf { it.amount }
    val totalBalance = totalIncome - totalExpense

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catatan Keuangan", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAdd,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Transaksi", tint = Color.White)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Card Saldo Ringkasan
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0EAEF))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Total Saldo", fontSize = 14.sp, color = Color.Gray)
                    Text(
                        text = formatRupiah(totalBalance),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.KeyboardArrowUp, contentDescription = null, tint = Color(0xFF2E7D32))
                                Text("Pemasukan", fontSize = 12.sp, color = Color.Gray)
                            }
                            Text(formatRupiah(totalIncome), fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                        }
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color(0xFFC62828))
                                Text("Pengeluaran", fontSize = 12.sp, color = Color.Gray)
                            }
                            Text(formatRupiah(totalExpense), fontWeight = FontWeight.Bold, color = Color(0xFFC62828))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text("Riwayat Transaksi", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            // Daftar Riwayat
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(transactions) { item ->
                    RecordItem(item = item, onDelete = { onDeleteTransaction(item) })
                }
            }
        }
    }
}

@Composable
fun RecordItem(item: Transaction, onDelete: () -> Unit) {
    val isIncome = item.type == "INCOME"
    val color = if (isIncome) Color(0xFF2E7D32) else Color(0xFFC62828)
    val prefix = if (isIncome) "+ " else "- "

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(color.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isIncome) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = color
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(item.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(item.date, fontSize = 12.sp, color = Color.Gray)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = prefix + formatRupiah(item.amount),
                    fontWeight = FontWeight.Bold,
                    color = color,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = Color.Gray)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    onBack: () -> Unit,
    onSave: (String, Double, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var amountStr by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("EXPENSE") } // Default Pengeluaran

    // State untuk Validasi Input
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Transaksi Baru") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Pilihan Tipe Transaksi
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { type = "EXPENSE" },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (type == "EXPENSE") Color(0xFFC62828) else Color.LightGray
                    )
                ) {
                    Text("Pengeluaran")
                }
                Button(
                    onClick = { type = "INCOME" },
                    modifier = Modifier.weight(1fK),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (type == "INCOME") Color(0xFF2E7D32) else Color.LightGray
                    )
                ) {
                    Text("Pemasukan")
                }
            }

            // Input Nama Transaksi
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Nama Transaksi (Contoh: Makan, Gaji)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Input Nominal Uang
            OutlinedTextField(
                value = amountStr,
                onValueChange = { amountStr = it },
                label = { Text("Nominal (Rp)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Pesan Error Validasi
            if (showError) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Tombol Simpan dengan Logika Validasi
            Button(
                onClick = {
                    // LOGIKA VALIDASI INPUT (Sesuai Syarat Tugas 2)
                    if (title.isBlank()) {
                        showError = true
                        errorMessage = "Nama transaksi tidak boleh kosong!"
                    } else if (amountStr.isBlank() || amountStr.toDoubleOrNull() == null || amountStr.toDouble() <= 0.0) {
                        showError = true
                        errorMessage = "Masukkan nominal angka yang valid dan lebih dari 0!"
                    } else {
                        showError = false
                        val amount = amountStr.toDouble()
                        onSave(title, amount, type) // Membawa hasil olahan data kembali ke layar utama
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Simpan Transaksi", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// Fungsi pembantu format mata uang Rupiah
fun formatRupiah(amount: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    return format.format(amount).replace("Rp", "Rp ").substringBefore(",")
}