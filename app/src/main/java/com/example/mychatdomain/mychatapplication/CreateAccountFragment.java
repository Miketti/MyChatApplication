package com.example.mychatdomain.mychatapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateAccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateAccountFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText emailedittext, usernameedittext, passwordedittext, confirmpasswordedittext;
    TextView invalidemailtext, invalidusernametext,invalidpasswordtext, invalidconfirmpasswordtext;
    Button create_account_button;

    private OnFragmentInteractionListener mListener;
    CallBackListener callbacklistener;


    public CreateAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateAccountFragment newInstance(String param1, String param2) {
        CreateAccountFragment fragment = new CreateAccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (emailedittext.getText().toString().trim().length() == 0) {
                    invalidemailtext.setText("Email cannot be empty");
                }
                else if (emailedittext.getText().toString().trim().length() > 0 && !android.util.Patterns.EMAIL_ADDRESS.matcher(emailedittext.getText().toString().trim()).matches()) {
                    invalidemailtext.setText("Invalid email address");
                }
                else {
                    invalidemailtext.setText("");
                }
                if (usernameedittext.getText().toString().trim().length() < 6) {
                    invalidusernametext.setText("Username must be at least 6 characters long");
                }
                else {
                    invalidusernametext.setText("");
                }
                if (passwordedittext.getText().toString().trim().length() < 6) {
                    invalidpasswordtext.setText("Password must be at least 6 characters long");
                }
                else {
                    invalidpasswordtext.setText("");
                }

                if (!confirmpasswordedittext.getText().toString().trim().equals(passwordedittext.getText().toString().trim())) {
                    invalidconfirmpasswordtext.setText("Passwords doesn't match");
                }
                else {
                    invalidconfirmpasswordtext.setText("");
                }
            }
        };

        emailedittext = (EditText) view.findViewById(R.id.createAccountEmailEditText);
        emailedittext.addTextChangedListener(textWatcher);
        invalidemailtext = (TextView) view.findViewById(R.id.createAccountInvalidEmailTextView);
        usernameedittext = (EditText) view.findViewById(R.id.createAccountUserNameEditText);
        usernameedittext.addTextChangedListener(textWatcher);
        invalidusernametext = (TextView) view.findViewById(R.id.createAccountInvalidUserNameTextView);
        passwordedittext = (EditText) view.findViewById(R.id.createAccountPasswordEditText);
        passwordedittext.addTextChangedListener(textWatcher);
        invalidpasswordtext = (TextView) view.findViewById(R.id.createAccountInvalidPasswordTextView);
        confirmpasswordedittext = (EditText) view.findViewById(R.id.createAccountPasswordAgainEditText);
        confirmpasswordedittext.addTextChangedListener(textWatcher);
        invalidconfirmpasswordtext = (TextView) view.findViewById(R.id.createAccountInvalidPasswordAgainTextView);
        create_account_button = (Button) view.findViewById(R.id.createAccountButton);
        create_account_button.setOnClickListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacklistener = (CallBackListener) context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if (invalidemailtext.getText().toString().trim().equals("") && invalidusernametext.getText().toString().trim().equals("") && invalidpasswordtext.getText().toString().trim().equals("") && invalidconfirmpasswordtext.getText().toString().trim().equals("")) {
            passRegistrationData();
        }
        else {
            Toast.makeText(getContext(), "Please fix the errors before continuing", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void passRegistrationData() {
        String email = emailedittext.getText().toString().trim();
        String username = usernameedittext.getText().toString();
        String password = passwordedittext.getText().toString();
        callbacklistener.onRegistrationDataPass(email, username, password);
    }
}
