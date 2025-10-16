import React from "react";

export const Login: React.FC = () => {
    return (
        <>
        <div style={{width: "100%", height: "100%", position: "relative", background: "white", overflow: "hidden", borderRadius: "19px"}}>
            <img style={{width: "735px", height: "1024px", left: "705px", top: "0px", position: "absolute"}} src="https://placehold.co/735x1024" />
            <div style={{width: "282px", height: "64px", left: "213px", top: "240px", position: "absolute", textAlign: "right", justifyContent: "center", display: "flex", flexDirection: "column", color: "black", fontSize: "40px", fontFamily: "Jomolhari", fontWeight: "400", wordWrap: "break-word"}}>Welcome back!</div>
            <div style={{left: "197px", top: "296px", position: "absolute", textAlign: "right", justifyContent: "center", display: "flex", flexDirection: "column", color: "#A5A1A1", fontSize: "16px", fontFamily: "Jomolhari", fontWeight: "400", wordWrap: "break-word"}}>Sign in to plan smarter and celebrate better.</div>
            <div style={{width: "606px", height: "448px", minWidth: "320px", minHeight: "236.85px", padding: "24px", left: "54px", top: "408px", position: "absolute", background: "var(--Background-Default-Default, white)", borderRadius: "8px", outline: "1px var(--Border-Default-Default, #D9D9D9) solid", outlineOffset: "-1px", flexDirection: "column", justifyContent: "flex-start", alignItems: "flex-start", gap: "24px", display: "inline-flex"}}>
        <div data-has-description="false" data-has-error="false" data-has-label="true" data-state="Default" data-value-type="Placeholder" style={{alignSelf: "stretch", flexDirection: "column", justifyContent: "flex-start", alignItems: "flex-start", gap: "8px", display: "flex"}}>
            <div style={{alignSelf: "stretch", color: "var(--Text-Default-Default, #1E1E1E)", fontSize: "20px", fontFamily: "Jomolhari", fontWeight: "400", lineHeight: "28px", wordWrap: "break-word"}}>Email</div>
            <div style={{alignSelf: "stretch", minWidth: "240px", paddingLeft: "16px", paddingRight: "16px", paddingTop: "12px", paddingBottom: "12px", background: "var(--Background-Default-Default, white)", overflow: "hidden", borderRadius: "8px", outline: "1px var(--Border-Default-Default, #D9D9D9) solid", outlineOffset: "-0.50px", justifyContent: "flex-start", alignItems: "center", display: "inline-flex"}}>
            <div style={{flex: "1 1 0", color: "var(--Text-Default-Tertiary, #B3B3B3)", fontSize: "18px", fontFamily: "Jomolhari", fontWeight: "400", lineHeight: "18px", wordWrap: "break-word"}}>Ex. Janedoe@example.com</div>
        </div>
        </div>
        <div data-has-description="false" data-has-error="false" data-has-label="true" data-state="Default" data-value-type="Placeholder" style={{alignSelf: "stretch", flexDirection: "column", justifyContent: "flex-start", alignItems: "flex-start", gap: "8px", display: "flex"}}>
            <div style={{alignSelf: "stretch", color: "var(--Text-Default-Default, #1E1E1E)", fontSize: "20px", fontFamily: "Jomolhari", fontWeight: "400", lineHeight: "28px", wordWrap: "break-word"}}>Password</div>
            <div style={{alignSelf: "stretch", minWidth: "240px", paddingLeft: "16px", paddingRight: "16px", paddingTop: "12px", paddingBottom: "12px", background: "var(--Background-Default-Default, white)", overflow: "hidden", borderRadius: "8px", outline: "1px var(--Border-Default-Default, #D9D9D9) solid", outlineOffset: "-0.50px", justifyContent: "flex-start", alignItems: "center", display: "inline-flex"}}>
            <div style={{flex: "1 1 0", color: "var(--Text-Default-Tertiary, #B3B3B3)", fontSize: "18px", fontFamily: "Jomolhari", fontWeight: "400", lineHeight: "18px", wordWrap: "break-word"}}>Enter password</div>
            </div>
        </div>
        </div>
        </div>
    </>
    );
}

export default Login;