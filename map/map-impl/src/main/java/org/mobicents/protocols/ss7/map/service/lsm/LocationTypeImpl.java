/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.mobicents.protocols.ss7.map.service.lsm;

import java.io.IOException;

import org.mobicents.protocols.asn.AsnException;
import org.mobicents.protocols.asn.AsnInputStream;
import org.mobicents.protocols.asn.AsnOutputStream;
import org.mobicents.protocols.asn.Tag;
import org.mobicents.protocols.ss7.map.api.MAPException;
import org.mobicents.protocols.ss7.map.api.MAPParsingComponentException;
import org.mobicents.protocols.ss7.map.api.MAPParsingComponentExceptionReason;
import org.mobicents.protocols.ss7.map.api.service.lsm.DeferredLocationEventType;
import org.mobicents.protocols.ss7.map.api.service.lsm.LocationEstimateType;
import org.mobicents.protocols.ss7.map.api.service.lsm.LocationType;
import org.mobicents.protocols.ss7.map.primitives.MAPAsnPrimitive;

/**
 * @author amit bhayani
 * 
 */
public class LocationTypeImpl implements LocationType, MAPAsnPrimitive {
	private static final int _TAG_LOCATION_ESTIMATE_TYPE = 0;
	private static final int _TAG_DEFERRED_LOCATION_EVET_TYPE = 1;

	private LocationEstimateType locationEstimateType;
	private DeferredLocationEventType deferredLocationEventType;

	public LocationTypeImpl() {

	}

	/**
	 * 
	 */
	public LocationTypeImpl(final LocationEstimateType locationEstimateType, final DeferredLocationEventType deferredLocationEventType) {
		this.locationEstimateType = locationEstimateType;
		this.deferredLocationEventType = deferredLocationEventType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.protocols.ss7.map.api.service.lsm.LocationType#
	 * getLocationEstimateType()
	 */
	@Override
	public LocationEstimateType getLocationEstimateType() {
		return this.locationEstimateType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.protocols.ss7.map.api.service.lsm.LocationType#
	 * getDeferredLocationEventType()
	 */
	@Override
	public DeferredLocationEventType getDeferredLocationEventType() {
		return this.deferredLocationEventType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#getTag()
	 */
	@Override
	public int getTag() throws MAPException {
		return Tag.SEQUENCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#getTagClass
	 * ()
	 */
	@Override
	public int getTagClass() {
		return Tag.CLASS_UNIVERSAL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#getIsPrimitive
	 * ()
	 */
	@Override
	public boolean getIsPrimitive() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#decodeAll
	 * (org.mobicents.protocols.asn.AsnInputStream)
	 */
	@Override
	public void decodeAll(AsnInputStream ansIS) throws MAPParsingComponentException {
		try {
			int length = ansIS.readLength();
			this._decode(ansIS, length);
		} catch (IOException e) {
			throw new MAPParsingComponentException("IOException when decoding SM_RP_DA: " + e.getMessage(), e,
					MAPParsingComponentExceptionReason.MistypedParameter);
		} catch (AsnException e) {
			throw new MAPParsingComponentException("AsnException when decoding SM_RP_DA: " + e.getMessage(), e,
					MAPParsingComponentExceptionReason.MistypedParameter);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#decodeData
	 * (org.mobicents.protocols.asn.AsnInputStream, int)
	 */
	@Override
	public void decodeData(AsnInputStream ansIS, int length) throws MAPParsingComponentException {
		try {
			this._decode(ansIS, length);
		} catch (IOException e) {
			throw new MAPParsingComponentException("IOException when decoding SM_RP_DA: " + e.getMessage(), e,
					MAPParsingComponentExceptionReason.MistypedParameter);
		} catch (AsnException e) {
			throw new MAPParsingComponentException("AsnException when decoding SM_RP_DA: " + e.getMessage(), e,
					MAPParsingComponentExceptionReason.MistypedParameter);
		}
	}

	private void _decode(AsnInputStream asnIS, int length) throws MAPParsingComponentException, IOException, AsnException {

		AsnInputStream ais = asnIS.readSequenceStreamData(length);

		int tag = ais.readTag();

		if (ais.getTagClass() != Tag.CLASS_CONTEXT_SPECIFIC || !ais.isTagPrimitive() || tag != _TAG_LOCATION_ESTIMATE_TYPE) {
			throw new MAPParsingComponentException(
					"Error while decoding LocationType: Parameter 0[locationEstimateType [0] LocationEstimateType] bad tag class, tag or not primitive",
					MAPParsingComponentExceptionReason.MistypedParameter);
		}

		int length1 = ais.readLength();
		int type = (int) ais.readIntegerData(length1);
		this.locationEstimateType = LocationEstimateType.getLocationEstimateType(type);

		while (true) {
			if (ais.available() == 0)
				break;

			switch (ais.readTag()) {
			case _TAG_DEFERRED_LOCATION_EVET_TYPE:
				if (ais.getTagClass() != Tag.CLASS_CONTEXT_SPECIFIC || !ais.isTagPrimitive()) {
					throw new MAPParsingComponentException(
							"Error while decoding LocationType: Parameter 1 [deferredLocationEventType [1] DeferredLocationEventType] bad tag class, tag or not primitive",
							MAPParsingComponentExceptionReason.MistypedParameter);
				}

				this.deferredLocationEventType = new DeferredLocationEventTypeImpl();
				((DeferredLocationEventTypeImpl)this.deferredLocationEventType).decodeAll(ais);
				break;
			default:
				// Do we care?
				ais.advanceElement();
				break;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#encodeAll
	 * (org.mobicents.protocols.asn.AsnOutputStream)
	 */
	@Override
	public void encodeAll(AsnOutputStream asnOs) throws MAPException {
		this.encodeAll(asnOs, Tag.CLASS_UNIVERSAL, Tag.SEQUENCE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#encodeAll
	 * (org.mobicents.protocols.asn.AsnOutputStream, int, int)
	 */
	@Override
	public void encodeAll(AsnOutputStream asnOs, int tagClass, int tag) throws MAPException {
		try {
			asnOs.writeTag(tagClass, false, tag);
			int pos = asnOs.StartContentDefiniteLength();
			this.encodeData(asnOs);
			asnOs.FinalizeContent(pos);
		} catch (AsnException e) {
			throw new MAPException("AsnException when encoding reportSMDeliveryStatusRequest: " + e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#encodeData
	 * (org.mobicents.protocols.asn.AsnOutputStream)
	 */
	@Override
	public void encodeData(AsnOutputStream asnOs) throws MAPException {
		if (this.locationEstimateType == null) {
			throw new MAPException("Error while encoding LocationType the mandatory parameter LocationEstimateType is not defined");
		}

		try {
			asnOs.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC, _TAG_LOCATION_ESTIMATE_TYPE, this.locationEstimateType.getType());
		} catch (IOException e) {
			throw new MAPException("IOException when encoding parameter locationEstimateType: ", e);
		} catch (AsnException e) {
			throw new MAPException("AsnException when encoding parameter locationEstimateType: ", e);
		}

		if (this.deferredLocationEventType != null) {
			((DeferredLocationEventTypeImpl)this.deferredLocationEventType).encodeAll(asnOs, Tag.CLASS_CONTEXT_SPECIFIC, _TAG_DEFERRED_LOCATION_EVET_TYPE);
		}
	}

}