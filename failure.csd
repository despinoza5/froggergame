; CS 3372, fall 2021
; Audio generation example: failure melody
;
; This Csound file, when given to csbuild.bat, generates audio files.

<CsoundSynthesizer>
<CsOptions>
</CsOptions>
<CsInstruments>

sr     = 44100
ksmps  = 1
nchnls = 2
0dbfs  = 1

giSine ftgen 0, 0, 2^16, 10, 1

; varying-power-of-sine instrument
;               p2        p3      p4  p5   p6       p7        p8
; i "sinepower" startTime noteDur amp freq maxPower attackDur decayDur
           instr      sinepower
p3         =          p3 + p8
iNoteDur   =          p3
iAmp       =          p4
iFreq      =          p5
iMaxPower  =          p6
iAttackDur =          p7
iDecayDur  =          p8
iCurve     =          2
kEnv       transeg    0, iAttackDur, -iCurve, iAmp, iNoteDur - iAttackDur - iDecayDur, -iCurve, iAmp / 2, iDecayDur, -iCurve, 0
kPower     transeg    1, iAttackDur, -iCurve, iMaxPower, iNoteDur - iAttackDur - iDecayDur, -iCurve, sqrt(iMaxPower), iDecayDur, -iCurve, 1
aSine      poscil3    1, iFreq, giSine
aPowered   powershape aSine, kPower
aOut       =          kEnv * aPowered
           outs       aOut, aOut
           endin

; FM synthesis instrument
;        p2        p3      p4  p5   p6        p7       p8        p9       p10
; i "fm" startTime noteDur amp freq attackDur decayDur carrRatio modRatio fmIndex
           instr   fm
p3         =       p3 + p7
iNoteDur   =       p3
iAmp       =       p4
iFreq      =       p5
iAttackDur =       p6
iDecayDur  =       p7
iCurve     =       2
kEnv       transeg 0, iAttackDur, iCurve, iAmp, iNoteDur - iAttackDur - iDecayDur, 0, iAmp, iDecayDur, -iCurve, 0
aOut       foscili kEnv, iFreq, p8, p9, p10, giSine
           outs    aOut, aOut
           endin

</CsInstruments>
<CsScore>
; background melody:
i "sinepower" 0.0 0.25 0.2 371.25 2.0 0.02 0.25
i "sinepower" 0.25 0.125 0.2 264.0 2.0 0.02 0.25
i "sinepower" 0.375 0.25 0.2 371.25 2.0 0.02 0.25
i "sinepower" 0.625 0.125 0.2 264.0 2.0 0.02 0.25
i "sinepower" 0.75 0.25 0.2 371.25 2.0 0.02 0.25
i "sinepower" 1.0 0.125 0.2 264.0 2.0 0.02 0.25
; sad melody:
i "fm" 0.0 0.125 0.2 495.0 0.02 0.25 1.0 2.23606797749979 1.0
i "fm" 0.125 0.375 0.2 475.2 0.02 0.25 1.0 2.23606797749979 1.0
i "fm" 0.5 0.125 0.2 297.0 0.02 0.25 1.0 2.23606797749979 1.0
i "fm" 0.625 0.5 0.2 281.6 0.02 0.25 1.0 2.23606797749979 1.0
e

</CsScore>
</CsoundSynthesizer>
