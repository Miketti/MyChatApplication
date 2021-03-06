package com.example.mychatdomain.mychatapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
 * {@link LogInFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LogInFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * This is LoginFragment. This fragment is shown during WelcomeActivity when user wants to log in.
 *
 */
public class LogInFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText loginusernamefield, loginpasswordfield;
    Button loginbutton;

    private OnFragmentInteractionListener mListener;
    //CallBackListener is used to listen callbacks between fragment and host activity.
    CallBackListener callbacklistener;

    public LogInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogInFragment newInstance(String param1, String param2) {
        LogInFragment fragment = new LogInFragment();
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
        View loginfragmentview = inflater.inflate(R.layout.fragment_log_in, container, false);
        loginusernamefield = (EditText) loginfragmentview.findViewById(R.id.logInUserNameEditText);
        loginpasswordfield = (EditText) loginfragmentview.findViewById(R.id.logInPasswordEditText);
        loginbutton = (Button)loginfragmentview.findViewById(R.id.logInButton);
        loginbutton.setOnClickListener(this);

        //This opens PasswordResetSendCodeFragment when the text is clicked.
        TextView forgotPasswordText = (TextView) loginfragmentview.findViewById(R.id.logInRecoverPasswordTextView);
        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.welcome_activity_id, new PasswordResetSendCodeFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        //This opens CreateAccountFragment when the text is clicked.
        TextView createAccountText = (TextView)loginfragmentview.findViewById(R.id.logInLinkSignUpTextView);
        createAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.welcome_activity_id, new CreateAccountFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return loginfragmentview;
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
        if (loginusernamefield.getText().toString().trim().length() == 0 || loginpasswordfield.getText().toString().trim().length() == 0) {
            Toast.makeText(getContext(), "Enter both email and password!", Toast.LENGTH_LONG).show();
        }
        else {
            passSignInData();
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

    //Login data will be passed to the CallBackListener
    public void passSignInData() {
        String email = loginusernamefield.getText().toString().trim();
        String password = loginpasswordfield.getText().toString().trim();
        callbacklistener.onSignInDataPass(email, password);
    }
}
