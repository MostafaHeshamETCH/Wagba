package com.example.wagba.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.wagba.adapters.RestaurantAdapter;
import com.example.wagba.databinding.ActivityHomepageBinding;
import com.example.wagba.models.MenuModel;
import com.example.wagba.models.RestaurantModel;
import com.example.wagba.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Homepage extends AppCompatActivity {

    ArrayList<RestaurantModel> restaurants;
    private ActivityHomepageBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference restaurantsRef = database.getReference("restaurants");
    DatabaseReference userRef = database.getReference("users");
    RestaurantAdapter restaurantAdapter;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    UserModel currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomepageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.orderHistoryIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent k = new Intent(Homepage.this, Orders.class);
                            startActivity(k);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        binding.cartShortcut.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent k = new Intent(Homepage.this, Cart.class);
                            startActivity(k);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        restaurants = new ArrayList<RestaurantModel>();
        restaurantAdapter = new RestaurantAdapter(restaurants);
        binding.userName.setText("");

        userRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                 currentUser = dataSnapshot.getValue(UserModel.class);
                 binding.userName.setText("Hi " + currentUser.getName() + "!");
                 if(currentUser.getCart() != null){
                     binding.cartShortcutNumber.setText(String.valueOf(currentUser.getCart().size()) + " items");
                 } else {
                     binding.cartShortcutNumber.setText("0 items");
                 }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("RT DB", "Failed to read value.", error.toException());
            }
        });

        restaurantsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // restaurants = (ArrayList<RestaurantModel>) dataSnapshot.getValue();
                restaurants.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    restaurants.add(postSnapshot.getValue(RestaurantModel.class));
                }
                restaurantAdapter = new RestaurantAdapter(restaurants);
                binding.resRecyclerView.setAdapter(restaurantAdapter);
                binding.resRecyclerView.setLayoutManager(new GridLayoutManager(Homepage.this, 2));
                Log.d("Test", restaurants.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("RT DB", "Failed to read value.", error.toException());
            }
        });




        binding.resRecyclerView.setAdapter(restaurantAdapter);
        binding.resRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        // adds bottom padding to recycler view to clear space fot cart
        float offsetPx = 300;
        BottomOffsetDecoration bottomOffsetDecoration = new BottomOffsetDecoration((int) offsetPx);
        binding.resRecyclerView.addItemDecoration(bottomOffsetDecoration);

        binding.signOutIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(Homepage.this, SignIn.class));
                        finish();
                    }
                }
        );
    }

    private void populateDatabaseWithRestaurants() {
        ArrayList<MenuModel> macMenu = new ArrayList<MenuModel>();
        macMenu.add(new MenuModel("Share Box", "Pick 2 sandwiches from Big Mac (beef/chicken) and McChicken + 2 sandwiches from Beefburger, cheeseburger and Chicken MacDo", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/eb1ad4a5-6c50-4c07-aec5-162f5b09266f.jpg", "170.0"));
        macMenu.add(new MenuModel("Big Tasty", "The distinctive flavour of Big Tasty sauce comes from smoke flavourings, spices and garlic", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/b4916779-b0af-43f9-b8f4-a0d299994178.jpg", "85.0"));
        macMenu.add(new MenuModel("Big Mac", "Two beef patties, that unbeatably tasty Big Mac sauce, melting signature cheese, crisp shredded lettuce, onions, pickles and a bun in the middle all between a toasted sesame seed bun", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/298f37b4-f9f7-44ea-a297-02cda66bd50b.jpg", "89.5"));
        macMenu.add(new MenuModel("Chicken Mac", "A delicious combination of breaded chicken patties, crisp lettuce, melting cheese, onions, pickles, and our special sauce, all framed between a toasted sesame seed bun", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/0e13cb43-dacb-4788-8c7d-2c0ab72c23e9.jpg", "123.0"));
        restaurants.add(new RestaurantModel("McDonald's", "https://www.eatthis.com/wp-content/uploads/sites/4/2021/06/mcdonalds-2.jpg?quality=82&strip=1", "4.5 (191)", "30min", macMenu));

        ArrayList<MenuModel> kfcMenu = new ArrayList<MenuModel>();
        kfcMenu.add(new MenuModel("Dinner Meal", "3 Pieces fried chicken, served with small French fries, coleslaw salad, bun and soft drink", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/9b755771-f591-4000-8afe-4715259fb390.jpg", "109.0"));
        kfcMenu.add(new MenuModel("Strips Box", "3 Pieces crispy strips, served with small French fries and bun", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/afdadd03-837c-41f4-9a81-1208b37b10b8.jpg", "54.0"));
        kfcMenu.add(new MenuModel("8 Piece", "8 pc chicken + 1 large coleslaw + family fries + 3 bun + 1 L Drink", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/334a3c4f-1a5d-4f30-b220-4d25e834c52d.jpg", "273.0"));
        kfcMenu.add(new MenuModel("Twister Combo", "twister sandwich with fries and drink", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/4be24199-ae10-4268-85bb-7ced5b6c8480.jpg", "65.0"));
        restaurants.add(new RestaurantModel("KFC", "https://static.dw.com/image/63713861_6.jpg", "3.8 (119)", "60min", kfcMenu));

        ArrayList<MenuModel> willysMenu = new ArrayList<MenuModel>();
        willysMenu.add(new MenuModel("Nacho Burger", "Beef patty, beef bacon, chili beef, caramelized onions, cheese sauce, jalapeno, jalapeno sauce, deep fried tortilla bread", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/3b81459b-ff10-429e-8850-2259beaecf46.jpg", "144.0"));
        willysMenu.add(new MenuModel("Smokehouse Burger", "Beef patty, beef bacon, fried onions, American cheese, hickory BBQ sauce, lettuce, tomato and Louisiana sauce", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/735c1ff8-4093-4ad2-962e-c0c5eaa749cd.jpg", "79.5"));
        willysMenu.add(new MenuModel("Brooklyn Burger", "Beef patty, mushrooms, American cheese, country sauce, mushroom sauce", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/6eb8a00c-3f2d-4e2b-8dce-e3c34bf5f3ad.jpg", "69.5"));
        willysMenu.add(new MenuModel("Nacho BBQ", "Beef patty, cheese sauce, beef bacon, frizzled fried onions, hickory BBQ sauce, Louisiana sauce, deep fried tortilla bread", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/a21c7776-3f3b-47b0-9509-2a0fabc3519b.jpg", "89.5"));
        restaurants.add(new RestaurantModel("Willy's", "https://fastly.4sqi.net/img/general/600x600/46997485_gmX5h6TIJhw7q_wBZSXCssATfjYqCuM11U-Yn7y1BL4.jpg", "4.2 (7)", "45min",willysMenu));

        ArrayList<MenuModel> papaMenu = new ArrayList<MenuModel>();
        papaMenu.add(new MenuModel("Chicken Ranch", "Grilled chicken, tomato, fresh mushroom with ranch sauce", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/8990eaa5-1e62-4d9e-bbdf-2a1d425a7ff3.jpg", "255.0"));
        papaMenu.add(new MenuModel("Pepperoni", "Loaded with pepperoni and extra mozzarella cheese", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/8f09c8a9-4349-4fae-8f85-0515f6a28f25.jpg", "245.0"));
        papaMenu.add(new MenuModel("Smoky Cheese", "Smoked veal, smoked cheese, cheddar, gouda, onion with creamy sauce", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/d401ccc1-f2b2-423c-831f-18c8784a625a.jpg", "275.0"));
        papaMenu.add(new MenuModel("Margherita", "Mozzarella cheese and pizza sauce", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/e42ffd24-6aa8-4cb1-a10b-54b9d2d344fb.jpg", "195.0"));
        restaurants.add(new RestaurantModel("Papa John's", "https://www.papajohns.com/restaurants-near-me-open-now/img/store-hero.jpg", "4.6 (109)", "60min", papaMenu));


        ArrayList<MenuModel> cityMenu = new ArrayList<MenuModel>();
        cityMenu.add(new MenuModel("El Wahsh", "Panne, crispy, katyousha, Crepeot dog", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/b7253eb1-793d-4808-b9e0-1abbedc9c1a7.jpg", "55.0"));
        cityMenu.add(new MenuModel("Mafia Crepe", "Panne, crispy, katyousha, strips, shish, cordon bleu", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/5499ddb5-1ce2-4e43-bc49-4c27cbb8f5b4.jpg", "70.0"));
        cityMenu.add(new MenuModel("El Saroukh", "Panne, crispy, katyousha, hot dog, burger, kofta, fries", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/b977b395-2b25-41b9-822c-e9461925ee4e.jpg", "60.0"));
        cityMenu.add(new MenuModel("Shatshout Crepe", "Crispy, katyusha, strips", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/a6bebd9d-797e-4fce-9aaa-d9f58bf14108.jpg", "55.0"));
        restaurants.add(new RestaurantModel("City Crepe", "https://1.bp.blogspot.com/-A56oFp21-Gs/YOjDBY6JurI/AAAAAAAAWts/zwdBeVU7bAwMWSKP30jiO4sZ2NDj2SAAwCLcBGAsYHQ/s16000/%25D8%25B3%25D9%258A%25D8%25AA%25D9%258A-%25D9%2583%25D8%25B1%25D9%258A%25D8%25A8.jpg", "2.6 (109)", "45min", cityMenu));

        ArrayList<MenuModel> tabaliMenu = new ArrayList<MenuModel>();
        tabaliMenu.add(new MenuModel("Original Taa'mia", "Taa'mia with rocca, tomatoes & tehina", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/79b2e5d5-7220-4e6f-a2b3-5a820e364929.jpg", "11.0"));
        tabaliMenu.add(new MenuModel("Foul with Lemon", "Foul with saffron lemon", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/d69e776b-28f1-44ef-8b49-b891fce1d4ca.jpg", "12.95"));
        tabaliMenu.add(new MenuModel("Feta Cheese", "Feta cheese with tomato slices", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/22c1d717-eea5-4e83-8042-8b3e26ea3de6.jpg", "18.95"));
        tabaliMenu.add(new MenuModel("Pastrami Scrambled Eggs", "Scrambled eggs with local pastrami", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/0939e49a-17ad-4caa-8653-013b7b5d4e33.jpg", "32.95"));
        restaurants.add(new RestaurantModel("Tabali", "https://i0.wp.com/www.oxodia.com/wp-content/uploads/2021/12/%D9%85%D8%B7%D8%B9%D9%85-Tabali-Bistro-oxodia-5.jpg?fit=1601%2C2048&ssl=1", "4.1 (109)", "45min", tabaliMenu));


        ArrayList<MenuModel> nagafMenu = new ArrayList<MenuModel>();
        nagafMenu.add(new MenuModel("Nagaf Foul", "Nagaf Amazing Foul", "https://www.simplyleb.com/wp-content/uploads/Foul-5-500x500.jpg", "4.0"));
        nagafMenu.add(new MenuModel("Fried Roomi Cheese", "Nagaf Amazing Fried Roomi Cheese", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/ae8b6916-9f6b-4367-92ab-8d892bb5650e.jpg", "12.0"));
        nagafMenu.add(new MenuModel("Nagaf Falafel", "Nagaf Amazing Falafel", "https://downshiftology.com/wp-content/uploads/2019/07/Falafel-7-500x500.jpg", "4.0"));
        nagafMenu.add(new MenuModel("Orzo Soup", "Nagaf Orzo Soup", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/4791df51-6d4a-421d-b105-e2e41fdf325b.jpg", "17.0"));
        restaurants.add(new RestaurantModel("Nagaf", "https://img.restaurantguru.com/r8c7-Nagaf-Restaurants-logo.jpg", "4.4 (109)", "30min", nagafMenu));

        ArrayList<MenuModel> dunkinMenu = new ArrayList<MenuModel>();
        dunkinMenu.add(new MenuModel("Choco Biscuit Donut", "DD Choco Biscuit Donut", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/987a41f7-ebf5-49cb-b1fe-45c757053170.jpg", "18.99"));
        dunkinMenu.add(new MenuModel("Dunkin' Iced Coffee", "DD Iced Coffee", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/14cd5161-3fdb-49ba-bfe8-dfe72ff92833.jpg", "38.0"));
        dunkinMenu.add(new MenuModel("Ring Bavarian Donut", "Filled Ring Bavarian Donut", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/23607ddd-dea2-4c81-9661-08e599178d2b.jpg", "18.99"));
        dunkinMenu.add(new MenuModel("Iced Latte", "DD Iced Latte", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/4b820b07-5449-4105-8ab1-7d6ebc5b1244.jpg", "71.92"));
        restaurants.add(new RestaurantModel("Dunkin", "https://stories.inspirebrands.com/wp-content/uploads/2021/07/Dunkin-Morocco.jpg", "4.0 (109)", "45min", dunkinMenu));


        ArrayList<MenuModel> IbnMenu = new ArrayList<MenuModel>();
        IbnMenu.add(new MenuModel("El Ma'alm Tray", "Charcoal chicken, 1/4 kofta with pine nuts, 1/4 shaqaf kebab, 1/4 shish tawook or kasta thighs, 3 bread bags, 5 mixed salads (tahini, green salad, mutabal, garlic dip, yogurt), 2 kilos rice, 7 grilled muhammara, pepsi liter, konafa plate", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/21034254-8e5f-4ce4-8e5b-2e65f708cba2.jpg", "574.0"));
        IbnMenu.add(new MenuModel("Jumbo Burger Offer", "2 beef or chicken burger sandwiches, large fries and pepsi liter", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/67a76f8d-3263-4f4f-85b0-5c612f66cdfa.jpg", "115.0"));
        IbnMenu.add(new MenuModel("Chicken Shawerma Saj", "Chicken Shawerma Saj Sandwich", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/0022068b-86b8-4cd4-b4f1-1a392a1760d0.jpg", "46.0"));
        IbnMenu.add(new MenuModel("Arabian Chicken", "Arabian Chicken Shawerma Meal Fries, garlic dip, coleslaw, pickles", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/5ac7437a-d4b5-4f81-90e1-e8a409672cdd.jpg", "84.0"));
        restaurants.add(new RestaurantModel("Ibn El Sham", "https://blogger.googleusercontent.com/img/a/AVvXsEiV4WHNoA6oyF8F43WMrd9HFWv8oSdgF38lbjrmV0iVxTIBpIRUIvbzzUH9V3pQy81-JI6oUD6opTfuM3QQ5_w0mVN1JwNNNOl3I3tNX25Y34_tnHGgCAcFZWsrVLWEpMmKHjsfopd-Aem8lNRwp8yRRBQknjjdwtldq7Rbi1tQdbwBEE8RS9DuvJrI=w640-h496", "4.0 (109)", "45min", IbnMenu));

        ArrayList<MenuModel> aboMenu = new ArrayList<MenuModel>();
        aboMenu.add(new MenuModel("Chicken Shawerma", "Chicken Shawerma Sandwich", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/8f690c61-4050-41ba-9164-81cebb4fd301.jpg", "43.0"));
        aboMenu.add(new MenuModel("Beef Shawerma", "Beef Shawerma Sandwich", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/70ef2455-1f0f-460b-8131-6c4b63827884.jpg", "50.0"));
        aboMenu.add(new MenuModel("Chicken Fattah", "Jumbo Chicken Shawerma Fattah", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/1fe8e110-ac00-4bad-ad2f-343b9d6d03a4.jpg", "75.0"));
        aboMenu.add(new MenuModel("Super Syrian", "Super Syrian Chicken Shawerma Sandwich", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/9a58d87b-25a6-4301-ad87-29b37677377e.jpg", "50.0"));
        restaurants.add(new RestaurantModel("Abo Mazen", "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/19d63f5d-c70e-4f9d-840f-aba03156ea95.jpg", "4.0 (109)", "45min", aboMenu));

        restaurantsRef.setValue(restaurants);
    }

    // dismiss keyboard when user clicks outside any input field
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    static class BottomOffsetDecoration extends RecyclerView.ItemDecoration {
        private int mBottomOffset;

        public BottomOffsetDecoration(int bottomOffset) {
            mBottomOffset = bottomOffset;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int dataSize = state.getItemCount();
            int position = parent.getChildAdapterPosition(view);
            if (dataSize > 0 && position == dataSize - 1) {
                outRect.set(0, 0, 0, mBottomOffset);
            } else {
                outRect.set(0, 0, 0, 0);
            }

        }
    }
}