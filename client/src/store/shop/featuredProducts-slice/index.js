
import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";

const initialState= {
    featuredProductList: [],
    loading: false,
    error: null,
};

const BASE_URL = import.meta.env.VITE_BACKEND_URL;  


export const fetchFeaturedProducts = createAsyncThunk(
  "featuredShopProducts/fetchFeaturedProducts",
  async () => {
    const response = await axios.get(
        `${BASE_URL}/api/shop/stats/most-bought`
    ); 

    return response.data;
  }
);

const featuredShopProductsSlice = createSlice({
  name: "featuredShopProducts",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchFeaturedProducts.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchFeaturedProducts.fulfilled, (state, action) => {
        state.featuredProductList = action.payload.data; 
        state.loading = false;
      })

      .addCase(fetchFeaturedProducts.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message;
      });
  },
});

export default featuredShopProductsSlice.reducer;
