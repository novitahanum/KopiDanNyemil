package com.example.kopidannyemil

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.Locale

class MainActivity : Activity() {

    // WARNA APLIKASI
    private val cream = Color.parseColor("#FFF8F1")
    private val white = Color.WHITE
    private val brown = Color.parseColor("#8B5E3C")
    private val darkBrown = Color.parseColor("#4B3022")
    private val muted = Color.parseColor("#927D6D")
    private val palePeach = Color.parseColor("#F4E6D8")
    private val softBorder = Color.parseColor("#E9D8C8")

    // DATA PENGGUNA
    private var registeredName = ""
    private var registeredEmail = ""
    private var registeredPassword = ""

    private var displayName = "Pengguna"
    private var savedAddress = ""

    // DATA PESANAN
    private val cart = mutableMapOf<String, Int>()
    private var lastOrderTotal = 0

    private var currentScreen = "splash"

    // DATA MENU
    private data class MenuItem(
        val id: String,
        val name: String,
        val description: String,
        val price: Int,
        val imageRes: Int
    )

    private val menuItems = listOf(
        MenuItem(
            "kopi_gula_aren",
            "Kopi Susu Gula Aren",
            "Espresso, susu, dan gula aren",
            22000,
            R.drawable.kopi_gula_aren
        ),
        MenuItem(
            "donat_cokelat",
            "Donat Cokelat",
            "Donat lembut dengan glaze cokelat",
            12000,
            R.drawable.donat_cokelat
        ),
        MenuItem(
            "sourdough",
            "Sourdough Loaf",
            "Roti fermentasi alami",
            35000,
            R.drawable.sourdough
        ),
        MenuItem(
            "cappuccino",
            "Cappuccino",
            "Espresso dengan foam susu lembut",
            25000,
            R.drawable.cappuccino
        ),
        MenuItem(
            "matcha_latte",
            "Matcha Latte",
            "Matcha Jepang dengan susu segar",
            27000,
            R.drawable.matcha_latte
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = cream
        window.navigationBarColor = cream

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
                    (if (android.os.Build.VERSION.SDK_INT >= 26) {
                        View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                    } else {
                        0
                    })

        showSplash()
    }

    // =====================================
    // SPLASH SCREEN
    // =====================================

    private fun showSplash() {
        currentScreen = "splash"

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setBackgroundColor(cream)
            setPadding(dp(24), dp(24), dp(24), dp(24))
        }

        layout.addView(
            makeText("☕", 64f, brown, true)
        )

        layout.addView(
            makeText("Kopi dan Nyemil", 28f, darkBrown, true).apply {
                gravity = Gravity.CENTER
                setPadding(0, dp(12), 0, dp(8))
            }
        )

        layout.addView(
            makeText(
                "Teman santai di setiap waktu",
                14f,
                muted
            ).apply {
                gravity = Gravity.CENTER
            }
        )

        setContentView(layout)

        Handler(Looper.getMainLooper()).postDelayed({
            if (currentScreen == "splash") {
                showWelcome()
            }
        }, 1500)
    }

    // =====================================
    // WELCOME SCREEN
    // =====================================

    private fun showWelcome() {
        currentScreen = "welcome"

        setContentView(buildPage("Kopi dan Nyemil", false) { content ->

            addSpace(content, 55)

            addText(content, "☕", 55f, brown, true).gravity =
                Gravity.CENTER

            addText(
                content,
                "Selamat Datang!",
                25f,
                darkBrown,
                true
            ).gravity = Gravity.CENTER

            addText(
                content,
                "Nikmati berbagai pilihan kopi dan camilan favoritmu.",
                14f,
                muted
            ).apply {
                gravity = Gravity.CENTER
                setPadding(dp(20), dp(8), dp(20), dp(25))
            }

            addPanel(content).apply {
                orientation = LinearLayout.VERTICAL

                addText(
                    this,
                    "Pesan kopi dan camilan kesukaanmu dengan mudah.",
                    15f,
                    darkBrown,
                    true
                ).gravity = Gravity.CENTER

                addSpace(this, 12)

                addText(
                    this,
                    "Temukan menu favorit dan nikmati pengalaman memesan yang praktis.",
                    13f,
                    muted
                ).gravity = Gravity.CENTER
            }

            addSpace(content, 24)

            content.addView(
                buttonView("Masuk", true) {
                    showLogin()
                },
                matchWrap()
            )

            addSpace(content, 12)

            content.addView(
                buttonView("Daftar Akun", false) {
                    showRegister()
                },
                matchWrap()
            )
        })
    }

    // =====================================
    // LOGIN
    // =====================================

    private fun showLogin() {
        currentScreen = "login"

        setContentView(buildPage("Masuk", false) { content ->

            addSpace(content, 25)

            addText(
                content,
                "Selamat Datang Kembali!",
                23f,
                darkBrown,
                true
            )

            addText(
                content,
                "Masukkan email dan password akunmu.",
                13f,
                muted
            )

            addSpace(content, 25)

            addText(content, "Email", 13f, darkBrown, true)

            val emailField = inputField(
                "Masukkan email",
                registeredEmail,
                InputType.TYPE_CLASS_TEXT or
                        InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            )

            content.addView(emailField, matchWrap())

            addSpace(content, 15)

            addText(content, "Password", 13f, darkBrown, true)

            val passwordField = inputField(
                "Masukkan password",
                "",
                InputType.TYPE_CLASS_TEXT or
                        InputType.TYPE_TEXT_VARIATION_PASSWORD
            )

            content.addView(passwordField, matchWrap())

            addSpace(content, 25)

            content.addView(
                buttonView("Masuk", true) {

                    val email = emailField.text.toString().trim()
                    val password = passwordField.text.toString()

                    if (
                        email == registeredEmail &&
                        registeredEmail.isNotBlank() &&
                        password == registeredPassword
                    ) {
                        displayName = registeredName
                        showHome()
                    } else if (
                        email == "demo" &&
                        password == "123456"
                    ) {
                        displayName = "Pengguna Demo"
                        showHome()
                    } else {
                        toast("Email atau password tidak sesuai.")
                    }
                },
                matchWrap()
            )

            addSpace(content, 15)

            content.addView(
                buttonView("Belum punya akun? Daftar", false) {
                    showRegister()
                },
                matchWrap()
            )

            addSpace(content, 15)

            addText(
                content,
                "Akun demo: demo | Password: 123456",
                12f,
                muted
            ).gravity = Gravity.CENTER
        })
    }

    // =====================================
    // REGISTER
    // =====================================

    private fun showRegister() {
        currentScreen = "register"

        setContentView(buildPage("Daftar Akun", false) { content ->

            addSpace(content, 15)

            addText(
                content,
                "Buat Akun Baru",
                23f,
                darkBrown,
                true
            )

            addText(
                content,
                "Isi informasi berikut untuk membuat akun.",
                13f,
                muted
            )

            addSpace(content, 20)

            addText(content, "Nama Lengkap", 13f, darkBrown, true)

            val nameField = inputField("Masukkan nama lengkap")
            content.addView(nameField, matchWrap())

            addSpace(content, 12)

            addText(content, "Email", 13f, darkBrown, true)

            val emailField = inputField(
                "Masukkan email",
                "",
                InputType.TYPE_CLASS_TEXT or
                        InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            )

            content.addView(emailField, matchWrap())

            addSpace(content, 12)

            addText(content, "Password", 13f, darkBrown, true)

            val passwordField = inputField(
                "Minimal 6 karakter",
                "",
                InputType.TYPE_CLASS_TEXT or
                        InputType.TYPE_TEXT_VARIATION_PASSWORD
            )

            content.addView(passwordField, matchWrap())

            addSpace(content, 12)

            addText(content, "Konfirmasi Password", 13f, darkBrown, true)

            val confirmField = inputField(
                "Ulangi password",
                "",
                InputType.TYPE_CLASS_TEXT or
                        InputType.TYPE_TEXT_VARIATION_PASSWORD
            )

            content.addView(confirmField, matchWrap())

            addSpace(content, 22)

            content.addView(
                buttonView("Daftar Sekarang", true) {

                    val name = nameField.text.toString().trim()
                    val email = emailField.text.toString().trim()
                    val password = passwordField.text.toString()
                    val confirm = confirmField.text.toString()

                    when {
                        name.isBlank() ||
                                email.isBlank() ||
                                password.isBlank() ||
                                confirm.isBlank() -> {
                            toast("Semua kolom harus diisi.")
                        }

                        !email.contains("@") -> {
                            toast("Format email tidak valid.")
                        }

                        password.length < 6 -> {
                            toast("Password minimal 6 karakter.")
                        }

                        password != confirm -> {
                            toast("Konfirmasi password tidak sesuai.")
                        }

                        else -> {
                            registeredName = name
                            registeredEmail = email
                            registeredPassword = password
                            displayName = name

                            toast("Pendaftaran berhasil!")
                            showLogin()
                        }
                    }
                },
                matchWrap()
            )

            addSpace(content, 12)

            content.addView(
                buttonView("Kembali ke Masuk", false) {
                    showLogin()
                },
                matchWrap()
            )
        })
    }

    // =====================================
    // HALAMAN BERANDA
    // =====================================

    private fun showHome() {
        currentScreen = "home"

        setContentView(buildPage("Beranda", true) { content ->

            addText(
                content,
                "Halo, $displayName! 👋",
                22f,
                darkBrown,
                true
            )

            addText(
                content,
                "Mau ngopi dan nyemil apa hari ini?",
                13f,
                muted
            )

            addSpace(content, 18)

            addPanel(content).apply {
                orientation = LinearLayout.VERTICAL
                background = rounded(brown, 18)

                addText(
                    this,
                    "Nikmati Hari dengan Kopi Favorit",
                    18f,
                    white,
                    true
                )

                addSpace(this, 5)

                addText(
                    this,
                    "Kopi enak dan camilan lezat menunggumu.",
                    12f,
                    white
                )
            }

            addSpace(content, 18)

            addText(
                content,
                "Menu Pilihan",
                19f,
                darkBrown,
                true
            )

            addSpace(content, 12)

            menuItems.forEach { item ->
                addMenuCard(content, item)
            }

            addSpace(content, 10)

            content.addView(
                buttonView("Lihat Pesanan", true) {
                    showOrder()
                },
                matchWrap()
            )
        })
    }

    // =====================================
    // KARTU MENU DENGAN FOTO
    // =====================================

    private fun addMenuCard(
        parent: LinearLayout,
        item: MenuItem
    ) {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(12), dp(12), dp(12), dp(12))
            background = rounded(white, 18, softBorder, 1)
        }

        val productImage = ImageView(this).apply {
            setImageResource(item.imageRes)
            scaleType = ImageView.ScaleType.CENTER_CROP
            background = rounded(palePeach, 16)
            clipToOutline = true
            contentDescription = item.name
        }

        card.addView(
            productImage,
            LinearLayout.LayoutParams(dp(82), dp(82))
        )

        val info = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(12), 0, dp(6), 0)
        }

        info.addView(
            makeText(item.name, 14f, darkBrown, true)
        )

        info.addView(
            makeText(item.description, 11f, muted).apply {
                maxLines = 2
                setPadding(0, dp(4), 0, dp(5))
            }
        )

        info.addView(
            makeText(rupiah(item.price), 13f, brown, true)
        )

        card.addView(
            info,
            LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
            )
        )

        val addButton = buttonView("+ Tambah", false) {
            cart[item.id] = (cart[item.id] ?: 0) + 1
            toast("${item.name} ditambahkan ke pesanan.")
        }.apply {
            textSize = 11f
            setPadding(dp(8), 0, dp(8), 0)
            minHeight = dp(40)
        }

        card.addView(
            addButton,
            LinearLayout.LayoutParams(dp(88), dp(42))
        )

        parent.addView(
            card,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dp(12)
            }
        )
    }

    // =====================================
    // HALAMAN PROFIL
    // =====================================

    private fun showProfile() {
        currentScreen = "profile"

        setContentView(buildPage("Profil Saya", true) { content ->

            addSpace(content, 15)

            addText(
                content,
                "Informasi Pengguna",
                21f,
                darkBrown,
                true
            )

            addSpace(content, 20)

            addText(content, "Nama Lengkap", 13f, darkBrown, true)

            val nameField = inputField(
                "Nama lengkap",
                displayName
            )

            content.addView(nameField, matchWrap())

            addSpace(content, 15)

            addText(content, "Email", 13f, darkBrown, true)

            val emailField = inputField(
                "Email",
                registeredEmail,
                InputType.TYPE_CLASS_TEXT or
                        InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            )

            content.addView(emailField, matchWrap())

            addSpace(content, 15)

            addText(content, "Alamat Pengiriman", 13f, darkBrown, true)

            val addressField = inputField(
                "Masukkan alamat lengkap",
                savedAddress,
                multiline = true
            )

            content.addView(
                addressField,
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    dp(100)
                )
            )

            addSpace(content, 22)

            content.addView(
                buttonView("Simpan Perubahan", true) {

                    if (nameField.text.toString().isBlank()) {
                        toast("Nama tidak boleh kosong.")
                    } else {
                        displayName = nameField.text.toString().trim()
                        registeredEmail = emailField.text.toString().trim()
                        savedAddress = addressField.text.toString().trim()

                        toast("Profil berhasil diperbarui.")
                        showProfile()
                    }
                },
                matchWrap()
            )

            addSpace(content, 12)

            content.addView(
                buttonView("Keluar", false) {
                    showWelcome()
                },
                matchWrap()
            )
        })
    }

    // =====================================
    // HALAMAN PESANAN
    // =====================================

    private fun showOrder() {
        currentScreen = "order"

        setContentView(buildPage("Pesanan Saya", true) { content ->

            addText(
                content,
                "Ringkasan Pesanan",
                21f,
                darkBrown,
                true
            )

            addSpace(content, 15)

            val orderedItems = menuItems.filter {
                (cart[it.id] ?: 0) > 0
            }

            if (orderedItems.isEmpty()) {
                addPanel(content).apply {
                    orientation = LinearLayout.VERTICAL

                    addText(
                        this,
                        "Keranjang masih kosong.",
                        16f,
                        darkBrown,
                        true
                    )

                    addText(
                        this,
                        "Silakan pilih menu terlebih dahulu.",
                        13f,
                        muted
                    )
                }

                content.addView(
                    buttonView("Pilih Menu", true) {
                        showHome()
                    },
                    matchWrap()
                )

                return@buildPage
            }

            orderedItems.forEach { item ->

                val quantity = cart[item.id] ?: 0

                val row = LinearLayout(this).apply {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER_VERTICAL
                    setPadding(dp(12), dp(12), dp(12), dp(12))
                    background = rounded(white, 15, softBorder, 1)
                }

                val itemInfo = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                }

                itemInfo.addView(
                    makeText(item.name, 14f, darkBrown, true)
                )

                itemInfo.addView(
                    makeText(
                        "${rupiah(item.price)} x $quantity",
                        12f,
                        muted
                    )
                )

                row.addView(
                    itemInfo,
                    LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                )

                row.addView(
                    makeText(
                        rupiah(item.price * quantity),
                        13f,
                        brown,
                        true
                    )
                )

                content.addView(
                    row,
                    matchWrap(bottom = 10)
                )
            }

            addSpace(content, 10)

            val total = calculateTotal()

            addPanel(content).apply {
                orientation = LinearLayout.VERTICAL

                addText(
                    this,
                    "Total Pembayaran",
                    14f,
                    muted
                )

                addText(
                    this,
                    rupiah(total),
                    22f,
                    brown,
                    true
                )
            }

            addSpace(content, 15)

            addText(
                content,
                "Alamat Pengiriman",
                14f,
                darkBrown,
                true
            )

            val addressField = inputField(
                "Masukkan alamat lengkap",
                savedAddress,
                multiline = true
            )

            content.addView(
                addressField,
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    dp(100)
                )
            )

            addSpace(content, 15)

            addText(
                content,
                "Metode Pembayaran",
                14f,
                darkBrown,
                true
            )

            addPanel(content).apply {
                orientation = LinearLayout.VERTICAL

                addText(
                    this,
                    "Cash on Delivery (COD)",
                    14f,
                    darkBrown,
                    true
                )

                addText(
                    this,
                    "Bayar langsung saat pesanan diterima.",
                    12f,
                    muted
                )
            }

            addSpace(content, 15)

            content.addView(
                buttonView("Konfirmasi Pesanan", true) {

                    val address = addressField.text.toString().trim()

                    if (address.isBlank()) {
                        toast("Alamat pengiriman harus diisi.")
                    } else {
                        savedAddress = address
                        lastOrderTotal = calculateTotal()
                        cart.clear()
                        showConfirmation()
                    }
                },
                matchWrap()
            )
        })
    }

    // =====================================
    // KONFIRMASI PESANAN
    // =====================================

    private fun showConfirmation() {
        currentScreen = "confirmation"

        setContentView(buildPage("Pesanan Berhasil", false) { content ->

            addSpace(content, 50)

            addText(
                content,
                "✓",
                65f,
                brown,
                true
            ).gravity = Gravity.CENTER

            addSpace(content, 12)

            addText(
                content,
                "Pesanan Berhasil!",
                24f,
                darkBrown,
                true
            ).gravity = Gravity.CENTER

            addText(
                content,
                "Terima kasih telah memesan di Kopi dan Nyemil.",
                14f,
                muted
            ).apply {
                gravity = Gravity.CENTER
                setPadding(dp(15), dp(10), dp(15), dp(20))
            }

            addPanel(content).apply {
                orientation = LinearLayout.VERTICAL

                addText(
                    this,
                    "Total Pembayaran",
                    14f,
                    muted
                )

                addText(
                    this,
                    rupiah(lastOrderTotal),
                    24f,
                    brown,
                    true
                )

                addSpace(this, 10)

                addText(
                    this,
                    "Alamat Pengiriman",
                    13f,
                    darkBrown,
                    true
                )

                addText(
                    this,
                    savedAddress,
                    13f,
                    muted
                )
            }

            addSpace(content, 20)

            content.addView(
                buttonView("Kembali ke Beranda", true) {
                    showHome()
                },
                matchWrap()
            )
        })
    }

    // =====================================
    // NAVIGASI BAWAH
    // =====================================

    private fun bottomNavigation(): View {
        val nav = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(dp(6), dp(5), dp(6), dp(5))
            setBackgroundColor(white)
        }

        val homeButton = smallNavButton("⌂\nBeranda") {
            showHome()
        }

        val orderButton = smallNavButton("🛒\nPesanan") {
            showOrder()
        }

        val profileButton = smallNavButton("♙\nProfil") {
            showProfile()
        }

        nav.addView(
            homeButton,
            LinearLayout.LayoutParams(0, dp(58), 1f)
        )

        nav.addView(
            orderButton,
            LinearLayout.LayoutParams(0, dp(58), 1f)
        )

        nav.addView(
            profileButton,
            LinearLayout.LayoutParams(0, dp(58), 1f)
        )

        return nav
    }

    private fun smallNavButton(
        label: String,
        action: () -> Unit
    ): TextView {
        return TextView(this).apply {
            text = label
            textSize = 12f
            gravity = Gravity.CENTER
            setTextColor(brown)
            setOnClickListener { action() }
        }
    }

    // =====================================
    // PEMBUAT HALAMAN
    // =====================================

    private fun buildPage(
        title: String,
        showNavigation: Boolean,
        contentBuilder: (LinearLayout) -> Unit
    ): View {

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(cream)
        }

        val header = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(18), dp(20), dp(14))
            setBackgroundColor(cream)
        }

        header.addView(
            makeText(title, 21f, darkBrown, true)
        )

        root.addView(
            header,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        val scrollView = ScrollView(this).apply {
            isFillViewport = true
        }

        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(10), dp(20), dp(20))
        }

        contentBuilder(content)

        scrollView.addView(content)

        root.addView(
            scrollView,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )

        if (showNavigation) {
            root.addView(
                bottomNavigation(),
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
        }

        return root
    }

    // =====================================
    // KOMPONEN TAMPILAN
    // =====================================

    private fun makeText(
        value: String,
        size: Float,
        color: Int,
        bold: Boolean = false
    ): TextView {
        return TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)

            if (bold) {
                setTypeface(null, Typeface.BOLD)
            }
        }
    }

    private fun addText(
        parent: LinearLayout,
        value: String,
        size: Float,
        color: Int,
        bold: Boolean = false
    ): TextView {
        val view = makeText(value, size, color, bold)

        parent.addView(
            view,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        return view
    }

    private fun inputField(
        hintText: String,
        initialValue: String = "",
        type: Int = InputType.TYPE_CLASS_TEXT,
        multiline: Boolean = false
    ): EditText {

        return EditText(this).apply {
            hint = hintText
            setText(initialValue)
            textSize = 14f
            setTextColor(darkBrown)
            setHintTextColor(muted)
            setPadding(dp(14), dp(12), dp(14), dp(12))
            background = rounded(white, 12, softBorder, 1)

            inputType = if (multiline) {
                InputType.TYPE_CLASS_TEXT or
                        InputType.TYPE_TEXT_FLAG_MULTI_LINE
            } else {
                type
            }

            if (multiline) {
                minLines = 3
                gravity = Gravity.TOP or Gravity.START
            } else {
                setSingleLine(true)
            }
        }
    }

    private fun buttonView(
        label: String,
        primary: Boolean,
        action: () -> Unit
    ): Button {

        return Button(this).apply {
            text = label
            textSize = 14f
            isAllCaps = false
            minHeight = dp(48)
            setPadding(dp(14), dp(8), dp(14), dp(8))

            if (primary) {
                setTextColor(white)
                background = rounded(brown, 14)
            } else {
                setTextColor(brown)
                background = rounded(white, 14, softBorder, 1)
            }

            setOnClickListener {
                action()
            }
        }
    }

    private fun addPanel(
        parent: LinearLayout
    ): LinearLayout {

        val panel = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(15), dp(15), dp(15), dp(15))
            background = rounded(white, 18, softBorder, 1)
        }

        parent.addView(
            panel,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dp(12)
            }
        )

        return panel
    }

    private fun addSpace(
        parent: LinearLayout,
        height: Int
    ) {
        parent.addView(
            View(this),
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(height)
            )
        )
    }

    private fun rounded(
        color: Int,
        radius: Int,
        strokeColor: Int = Color.TRANSPARENT,
        strokeWidth: Int = 0
    ): GradientDrawable {

        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = dp(radius).toFloat()

            if (strokeWidth > 0) {
                setStroke(dp(strokeWidth), strokeColor)
            }
        }
    }

    private fun matchWrap(
        bottom: Int = 0
    ): LinearLayout.LayoutParams {

        return LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            bottomMargin = dp(bottom)
        }
    }

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }

    // =====================================
    // PERHITUNGAN PESANAN
    // =====================================

    private fun calculateTotal(): Int {
        return menuItems.sumOf { item ->
            item.price * (cart[item.id] ?: 0)
        }
    }

    private fun rupiah(amount: Int): String {
        val formatted = String.format(
            Locale.US,
            "%,d",
            amount
        ).replace(",", ".")

        return "Rp$formatted"
    }

    private fun toast(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    // =====================================
    // TOMBOL KEMBALI
    // =====================================

    @SuppressLint("GestureBackNavigation")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        when (currentScreen) {
            "splash" -> super.onBackPressed()
            "welcome" -> super.onBackPressed()
            "login", "register" -> showWelcome()
            "home" -> showWelcome()
            "profile", "order" -> showHome()
            "confirmation" -> showHome()
            else -> super.onBackPressed()
        }
    }
}